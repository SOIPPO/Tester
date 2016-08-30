package org.soippo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.soippo.entity.Group;
import org.soippo.entity.GroupModules;
import org.soippo.entity.Module;
import org.soippo.entity.User;
import org.soippo.exceptions.UserValidationException;
import org.soippo.service.GroupService;
import org.soippo.service.ModuleService;
import org.soippo.service.UserResultsService;
import org.soippo.service.UserService;
import org.soippo.utils.UserRoles;
import org.soippo.utils.View;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private UserService userService;
    @Resource
    private GroupService groupService;
    @Resource
    private ModuleService moduleService;
    @Resource
    private UserResultsService userResultsService;

    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
        return "redirect:modules";
    }

    @RequestMapping(value = "/userlist", method = RequestMethod.POST)
    @ResponseBody
    public String userList() throws JsonProcessingException {
        return objectMapper
                .writerWithView(View.Extended.class)
                .writeValueAsString(userService.findAll());
    }

    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    public ModelAndView userListPage(ModelAndView model) throws JsonProcessingException {
        model.addObject("grouplist", objectMapper
                .writeValueAsString(groupService.findAll()));
        model.addObject("rolesList", objectMapper.writeValueAsString(UserRoles.values()));
        model.setViewName("userlist");

        return model;
    }

    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody String userData) throws IOException {
        try {
            return ResponseEntity.ok(objectMapper
                    .writeValueAsString(userService
                            .saveUser(objectMapper
                                    .readValue(userData, User.class))));
        } catch (UserValidationException ex) {
            return ResponseEntity.badRequest().body(objectMapper.writeValueAsString(ex.getErrorCode()));
        }
    }

    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/grouplist", method = RequestMethod.POST)
    @ResponseBody
    public String groupList() throws JsonProcessingException {
        List<Group> groups = groupService.findAll();
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
                .modules(new JavaTimeModule())
                .build()
                .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                .writerWithView(View.Normal.class)
                .writeValueAsString(groups);
    }

    @RequestMapping(value = "/grouplist", method = RequestMethod.GET)
    public ModelAndView groupListPage(ModelAndView model) throws JsonProcessingException {
        model.addObject("moduleList", moduleList());
        model.setViewName("grouplist");
        return model;
    }

    @RequestMapping(value = "/savegroup", method = RequestMethod.POST)
    public ResponseEntity saveGroup(@RequestBody String groupData) throws IOException {
        Group group = new Group()
                .setName(objectMapper.readTree(groupData).get("name").asText());

        if(objectMapper.readTree(groupData).get("id") != null)
            group.setId(Long.parseLong(objectMapper.readTree(groupData).get("id").asText()));

        String modulesData = objectMapper.readTree(groupData).get("modules").toString();
        List<Module> modules = Arrays.asList(objectMapper.readValue(modulesData, Module[].class));

        Function<String, LocalDate> parseDateFromNode = item ->  LocalDate.parse(item, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        LocalDate dateBegin = parseDateFromNode.apply(objectMapper.readTree(groupData).get("incoming_date").asText());
        LocalDate dateEnd = parseDateFromNode.apply(objectMapper.readTree(groupData).get("final_date").asText());

        List<GroupModules> groupModules = modules.stream()
                .map(item -> new GroupModules()
                        .setModule(item)
                        .setIncomingDate(dateBegin)
                        .setFinalDate(dateEnd))
                .collect(Collectors.toList());

        group.setGroupModules(groupModules);
        groupService.save(group);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/deletegroup", method = RequestMethod.POST)
    public ResponseEntity saveGroup(@RequestBody Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/checkgroup", method = RequestMethod.POST)
    public ResponseEntity checkGroupNameAvailability(@RequestBody String name) {
        if (groupService.checkGroupNameAvailability(name)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/modules", method = RequestMethod.GET)
    public ModelAndView interviewListPage(ModelAndView model) {
        model.addObject("interviewlist", moduleList());
        model.setViewName("admin-modules");
        return model;
    }

    @RequestMapping(value = "/interview/list", method = RequestMethod.POST)
    @ResponseBody
    public String moduleList() {
        try {
            return objectMapper
                    .writerWithView(View.Normal.class)
                    .writeValueAsString(moduleService.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/editmodule/{id}", method = RequestMethod.GET)
    public ModelAndView editinterviewPage(@PathVariable Long id, ModelAndView model) throws JsonProcessingException {
        model.addObject("interviewdata", objectMapper.writeValueAsString(moduleService.findOne(id)));
        model.setViewName("editmodule");
        return model;
    }

    @RequestMapping(value = "/interview/new", method = RequestMethod.POST)
    public ResponseEntity createNewInterview(@RequestBody String interviewTitle) {
        Module module = new Module().setTitle(interviewTitle);
        moduleService.save(module);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/interview/delete", method = RequestMethod.POST)
    public ResponseEntity deleteInterview(@RequestBody Long interviewId) {
        moduleService.delete(interviewId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/interview/save", method = RequestMethod.POST)
    public ResponseEntity<Module> saveInterview(@RequestBody String interviewData) throws IOException {
        Module module = objectMapper.readValue(interviewData, Module.class);
        return new ResponseEntity<>(moduleService.save(module), HttpStatus.OK);
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public ModelAndView resultsPage(ModelAndView model) throws JsonProcessingException {
        model.addObject("results", objectMapper
                .writerWithView(View.Simplified.class)
                .writeValueAsString(userResultsService.collectResults()));
        model.setViewName("usersresults");
        return model;
    }
}
