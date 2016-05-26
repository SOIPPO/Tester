package org.soippo.controller;

import com.google.gson.GsonBuilder;
import org.soippo.entity.User;
import org.soippo.service.GroupService;
import org.soippo.service.SerializeService;
import org.soippo.service.UserService;
import org.soippo.utils.GroupWithUserlistSerializer;
import org.soippo.utils.GroupWithoutUserlistSerializer;
import org.soippo.utils.UserSerializer;
import org.soippo.utils.UserSimplifiedSerializer;
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
    @Resource
    private SerializeService serializeService;

    @RequestMapping("/")
    public ModelAndView homePage(ModelAndView model) {
        model.addObject("user_message", "Hello world!");
        model.setViewName("/");
        return model;
    }

    @RequestMapping(value = "/simplegrouplist")
    @ResponseBody
    public String simpleGroupList() {
        return serializeService.serializeGroup(new UserSerializer(), new GroupWithoutUserlistSerializer());
    }

    @RequestMapping(value = "/fullgrouplist")
    @ResponseBody
    public String extendedGroupList() {
        return serializeService.serializeGroup(new UserSerializer(), new GroupWithUserlistSerializer());
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


}
