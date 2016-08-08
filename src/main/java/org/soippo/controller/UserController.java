package org.soippo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.soippo.entity.User;
import org.soippo.entity.UserResults;
import org.soippo.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
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

    private FilterProvider excludeUsersFilter = new SimpleFilterProvider()
            .addFilter("excludeUsers", SimpleBeanPropertyFilter.serializeAllExcept("users"));

    @RequestMapping("/")
    public String homePage(ModelAndView model) {
        return "redirect:modules";
//        userResultsService.findAll();
//        model.addObject("user_message", "Hello world!");
//        model.setViewName("/");
//        return model;
    }

    @RequestMapping("/modules")
    public ModelAndView interviewlistPage(ModelAndView model) {
        model.addObject("interviewlist", moduleService.findAll());
        model.setViewName("modules");
        return model;
    }

    @RequestMapping("/module/{id}")
    public ModelAndView modulePage(ModelAndView model, @PathVariable Long id) throws JsonProcessingException {
        model.addObject("moduleData", new ObjectMapper().writeValueAsString(moduleService.findOne(id)));
        model.setViewName("module");
        return model;
    }

    @RequestMapping(value = "/module/saveresults", method = RequestMethod.POST)
    @ResponseBody
    public String saveModuleResults(@RequestBody String moduleData) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(auth.getName());
        Map<Long, List<Long>> temporalDataMap = new ObjectMapper()
                .readValue(moduleData, new TypeReference<Map<Long, List<Long>>>(){});

        List<UserResults> userResults = temporalDataMap.entrySet()
                .stream()
                .map(item -> new UserResults()
                        .setUserId(userId)
                        .setQuestionId(item.getKey())
                        .setText(String.valueOf(item.getValue())))
                .collect(Collectors.toList());
        userResultsService.saveAll(userResults);

        return new ObjectMapper().writeValueAsString(questionService.checkAnswers(temporalDataMap));
    }

    @RequestMapping(value = "/api/userlistbygroup", method = RequestMethod.GET)
    @ResponseBody
    public String userListByGroup(@RequestParam(name = "group_id") Long groupId) throws JsonProcessingException {
        List<User> users = userService.findUsersInGroup(groupService.findGroup(groupId));
        return new ObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .writer(excludeUsersFilter)
                .writeValueAsString(users);
    }
}
