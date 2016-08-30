package org.soippo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.soippo.entity.User;
import org.soippo.entity.UserDetails;
import org.soippo.entity.UserResults;
import org.soippo.exceptions.UserValidationException;
import org.soippo.service.*;
import org.soippo.utils.View;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private GroupService groupService;
    @Resource
    private UserResultsService userResultsService;
    @Resource
    private ModuleService moduleService;
    @Resource
    private QuestionService questionService;

    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

    @RequestMapping("/")
    public String homePage() {
        return "redirect:modules";
    }

    @RequestMapping("/modules")
    public ModelAndView interviewlistPage(ModelAndView model) {
        Long userId = getCurrentUser().getId();
        model.addObject("modulelist", moduleService.availableModulesForUser(userId));
        model.setViewName("modules");
        return model;
    }

    @RequestMapping(value = "/interview/list", method = RequestMethod.POST)
    @ResponseBody
    public String moduleList() {
        try {
            return objectMapper
                    .writerWithView(View.Simplified.class)
                    .writeValueAsString(moduleService.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/user-modules/list", method = RequestMethod.POST)
    @ResponseBody
    public String userModuleList() {
        try {
            return objectMapper
                    .writerWithView(View.Simplified.class)
                    .writeValueAsString(moduleList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/results")
    public ModelAndView resultsPage(ModelAndView model) throws JsonProcessingException {
        Long userId = getCurrentUser().getId();
        model.addObject("results", objectMapper
                .writerWithView(View.Simplified.class)
                .writeValueAsString(userResultsService.collectResultsByUser(userId)));
        model.setViewName("results");
        return model;
    }

    @RequestMapping("/module/{id}")
    public ModelAndView modulePage(ModelAndView model, @PathVariable Long id) throws JsonProcessingException {
        if(userService.isModuleAvailableForUser(getCurrentUser().getId(), id)) {
            model.addObject("moduleData", objectMapper.writeValueAsString(moduleService.findOne(id)));
            model.setViewName("module");
            return model;
        } else {
            return new ModelAndView("redirect:/modules");
        }
    }

    @RequestMapping(value = "/module/saveresults", method = RequestMethod.POST)
    @ResponseBody
    public String saveModuleResults(@RequestBody String moduleData) throws IOException {
        Long userId = getCurrentUser().getId();
        Map<Long, List<Long>> temporalDataMap = objectMapper
                .readValue(moduleData, new TypeReference<Map<Long, List<Long>>>() {
                });
        Map<Long, Boolean> result = questionService.checkAnswers(temporalDataMap);
        List<UserResults> userResults = temporalDataMap.entrySet()
                .stream()
                .map(item -> new UserResults()
                        .setUserId(userId)
                        .setQuestionId(item.getKey())
                        .setDate(new Date(Calendar.getInstance().getTime().getTime()))
                        .setIsCorrect(result.get(item.getKey()))
                        .setText(String.valueOf(item.getValue())))
                .collect(Collectors.toList());
        userResultsService.saveAll(userResults);

        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping(value = "/api/userlistbygroup", method = RequestMethod.GET)
    @ResponseBody
    public String userListByGroup(@RequestParam(name = "group_id") Long groupId) throws JsonProcessingException {
        List<User> users = userService.findUsersInGroup(groupService.findGroup(groupId));
        return objectMapper
                .writerWithView(View.Simplified.class)
                .writeValueAsString(users);
    }

    @RequestMapping("/profile")
    public ModelAndView profilePage(ModelAndView model) throws JsonProcessingException {
        Long userId = getCurrentUser().getId();
        model.addObject("userData", objectMapper
                .writerWithView(View.Simplified.class)
                .writeValueAsString(userService.findOne(userId)));
        model.addObject("grouplist", objectMapper
                .writerWithView(View.Simplified.class)
                .writeValueAsString(groupService.findAll()));
        model.setViewName("profile");
        return model;
    }

    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody String userData) throws IOException {
        try {
            User user = objectMapper.readValue(userData, User.class);
            User savedUser = userService.findOne(user.getId());
            user.setRole(savedUser.getRole());
            return ResponseEntity.ok(objectMapper.writeValueAsString(userService.saveUser(user)));
        } catch (UserValidationException ex) {
            return ResponseEntity.badRequest().body(objectMapper.writeValueAsString(ex.getErrorCode()));
        }
    }

    private UserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) auth.getPrincipal();
    }
}
