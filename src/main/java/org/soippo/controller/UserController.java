package org.soippo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.soippo.entity.User;
import org.soippo.entity.UserResults;
import org.soippo.serialization.GroupWithUserlistSerializer;
import org.soippo.serialization.GroupWithoutUserlistSerializer;
import org.soippo.serialization.UserSerializer;
import org.soippo.serialization.UserSimplifiedSerializer;
import org.soippo.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.lang.reflect.Type;
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
    private SerializeService serializeService;
    @Resource
    private UserResultsService userResultsService;
    @Resource
    private InterviewService interviewService;

    @RequestMapping("/")
    public ModelAndView homePage(ModelAndView model) {
        model.addObject("user_message", "Hello world!");
        model.setViewName("/");
        return model;
    }

    @RequestMapping("/modules")
    public ModelAndView interviewlistPage(ModelAndView model) {
        model.addObject("interviewlist", interviewService.findAll());
        model.setViewName("modules");
        return model;
    }

    @RequestMapping("/module/{id}")
    public ModelAndView modulePage(ModelAndView model, @PathVariable Long id) {
        model.addObject("moduleData", new Gson().toJson(interviewService.findOne(id)));
        model.setViewName("module");
        return model;
    }

    @RequestMapping(value = "/module/saveresults", method = RequestMethod.POST)
    @ResponseBody
    public String saveModuleResults(@RequestBody String moduleData) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(auth.getName());
        Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
        Map<String, List<String>> temporalDataMap = new Gson().fromJson(moduleData, type);
        List<UserResults> userResults = temporalDataMap.entrySet()
                .stream()
                .map(item -> new UserResults()
                        .setUserId(userId)
                        .setQuestionId(Long.parseLong(item.getKey()))
                        .setText(String.valueOf(item.getValue())))
                .collect(Collectors.toList());
        userResultsService.saveAll(userResults);
        return moduleData;
    }


    @RequestMapping(value = "/api/simplegrouplist")
    @ResponseBody
    public String simpleGroupList() {
        return serializeService.serializeGroup(new UserSerializer(), new GroupWithoutUserlistSerializer());
    }

    @RequestMapping(value = "/api/fullgrouplist")
    @ResponseBody
    public String extendedGroupList() {
        return serializeService.serializeGroup(new UserSerializer(), new GroupWithUserlistSerializer());
    }

    @RequestMapping(value = "/api/userlistbygroup", method = RequestMethod.GET)
    @ResponseBody
    public String userListByGroup(@RequestParam(name = "group_id") Long groupId) {
        List<User> users = userService.findUsersInGroup(groupService.findGroup(groupId));
        return new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSimplifiedSerializer())
                .create()
                .toJson(users);
    }
}
