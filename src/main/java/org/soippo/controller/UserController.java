package org.soippo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import org.soippo.entity.Group;
import org.soippo.entity.User;
import org.soippo.exceptions.UserValidationException;
import org.soippo.service.GroupService;
import org.soippo.service.UserService;
import org.soippo.utils.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private GroupService groupService;

    @RequestMapping("/")
    public ModelAndView homePage(ModelAndView model) {
        model.addObject("user_message", "Hello world!");
        model.setViewName("home");
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerPage(ModelAndView model) {
        model.addObject("grouplist", serializeGroup(new UserSerializer(), new GroupWithoutUserlistSerializer()));
        model.setViewName("register");
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(ModelAndView model) {
        model.addObject("grouplist", groupService.groupList());
        model.setViewName("login");
        return model;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody String userData) {
        User user = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserDeserializer())
                .create()
                .fromJson(userData, User.class);

        try {
            return ResponseEntity.ok(new GsonBuilder()
                    .registerTypeAdapter(User.class, new UserSerializer())
                    .create()
                    .toJson(userService.saveUser(user)));
        } catch (UserValidationException ex) {
            return ResponseEntity.badRequest().body(new Gson().toJson(ex.getErrorCode()));
        }
    }

    @RequestMapping(value = "/simplegrouplist")
    @ResponseBody
    public String simpleGroupList() {
        return serializeGroup(new UserSerializer(), new GroupWithoutUserlistSerializer());
    }

    @RequestMapping(value = "/fullgrouplist")
    @ResponseBody
    public String extendedGroupList() {
        return serializeGroup(new UserSerializer(), new GroupWithUserlistSerializer());
    }

    @RequestMapping(value = "/userlistbygroup", method = RequestMethod.GET)
    @ResponseBody
    public String userListByGroup(@RequestParam(name = "group_id") Long groupId) {
        List<User> users = userService.findUsersInGroup(groupService.findGroup(groupId));
        return new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSimplifiedSerializer())
                .create()
                .toJson(users);
    }

    @RequestMapping(value = "/messages/{message}")
    @ResponseBody
    public ModelAndView displayMessages(ModelAndView model, @PathVariable String message) {
        model.addObject("message", message);
        model.setViewName("messages");
        return model;
    }

    private String serializeGroup(JsonSerializer<User> userSerializer, JsonSerializer<Group> groupSerializer) {
        return new GsonBuilder()
                .registerTypeAdapter(User.class, userSerializer)
                .registerTypeAdapter(Group.class, groupSerializer)
                .create()
                .toJson(groupService.groupList());
    }
}
