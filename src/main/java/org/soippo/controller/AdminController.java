package org.soippo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.Gson;
import org.soippo.entity.Group;
import org.soippo.entity.Module;
import org.soippo.entity.User;
import org.soippo.exceptions.UserValidationException;
import org.soippo.service.*;
import org.soippo.utils.UserRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private UserService userService;
    @Resource
    private GroupService groupService;
    @Resource
    private ModuleService moduleService;

    private FilterProvider excludeUsersFilter = new SimpleFilterProvider()
            .addFilter("excludeUsers", SimpleBeanPropertyFilter.serializeAllExcept("users", "user"));

    @RequestMapping(value = "/userlist", method = RequestMethod.POST)
    @ResponseBody
    public String userList() throws JsonProcessingException {
        return new ObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .writer(excludeUsersFilter)
                .writeValueAsString(userService.findAll());
    }

    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    public ModelAndView userListPage(ModelAndView model) throws JsonProcessingException {
        model.addObject("grouplist", new ObjectMapper().writer(excludeUsersFilter).writeValueAsString(groupService.findAll()));
        model.addObject("rolesList", new ObjectMapper().writeValueAsString(UserRoles.values()));
        model.setViewName("/userlist");
        return model;
    }

    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody String userData) throws IOException {
        try {
            return ResponseEntity.ok(new ObjectMapper()
                    .writer(excludeUsersFilter)
                    .writeValueAsString(userService
                            .saveUser(new ObjectMapper()
                                    .readValue(userData, User.class))));
        } catch (UserValidationException ex) {
            return ResponseEntity.badRequest().body(new Gson().toJson(ex.getErrorCode()));
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
        return new ObjectMapper().writer(excludeUsersFilter).writeValueAsString(groupService.findAll());
    }

    @RequestMapping(value = "/grouplist", method = RequestMethod.GET)
    public ModelAndView groupListPage(ModelAndView model) throws JsonProcessingException {
        model.addObject("grouplist", new ObjectMapper().writer(excludeUsersFilter).writeValueAsString(groupService.findAll()));
        model.setViewName("/grouplist");
        return model;
    }

    @RequestMapping(value = "/savegroup", method = RequestMethod.POST)
    public ResponseEntity saveGroup(@RequestBody String groupData) throws IOException {
        groupService.saveGroup(new ObjectMapper().readValue(groupData, Group.class));
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
        model.setViewName("/modules");
        return model;
    }

    @RequestMapping(value = "/interview/list", method = RequestMethod.POST)
    @ResponseBody
    public String moduleList() {
        try {
            return new ObjectMapper().writeValueAsString(moduleService.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/editmodule/{id}", method = RequestMethod.GET)
    public ModelAndView editinterviewPage(@PathVariable Long id, ModelAndView model) throws JsonProcessingException {
        model.addObject("interviewdata", new ObjectMapper().writeValueAsString(moduleService.findOne(id)));
        model.setViewName("/editmodule");
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
        Module module = new ObjectMapper().readValue(interviewData, Module.class);
        return new ResponseEntity<>(moduleService.save(module), HttpStatus.OK);
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public ModelAndView resultsPage(ModelAndView model) {
        model.setViewName("/usersresults");
        return model;
    }
}
