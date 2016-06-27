package org.soippo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.soippo.entity.Group;
import org.soippo.entity.Interview;
import org.soippo.entity.User;
import org.soippo.exceptions.UserValidationException;
import org.soippo.service.GroupService;
import org.soippo.service.InterviewService;
import org.soippo.service.SerializeService;
import org.soippo.service.UserService;
import org.soippo.serialization.GroupWithoutUserlistSerializer;
import org.soippo.serialization.UserDeserializer;
import org.soippo.utils.UserRoles;
import org.soippo.serialization.UserSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private UserService userService;
    @Resource
    private GroupService groupService;
    @Resource
    private SerializeService serializeService;
    @Resource
    private InterviewService interviewService;

    @RequestMapping(value = "/userlist", method = RequestMethod.POST)
    @ResponseBody
    public String userList() {
        List<User> users = userService.findAll();
        return new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .create()
                .toJson(users);
    }

    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    public ModelAndView userListPage(ModelAndView model) {
        model.addObject("grouplist", serializeService.serializeGroup(new UserSerializer(), new GroupWithoutUserlistSerializer()));
        model.addObject("rolesList", new Gson().toJson(UserRoles.values()));
        model.setViewName("/userlist");
        return model;
    }

    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody String userData) {
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

    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/grouplist", method = RequestMethod.POST)
    @ResponseBody
    public String groupList() {
        return serializeService.serializeGroup(new UserSerializer(), new GroupWithoutUserlistSerializer());
    }

    @RequestMapping(value = "/grouplist", method = RequestMethod.GET)
    public ModelAndView groupListPage(ModelAndView model) {
        model.addObject("grouplist", serializeService.serializeGroup(new UserSerializer(), new GroupWithoutUserlistSerializer()));
        model.setViewName("/grouplist");
        return model;
    }

    @RequestMapping(value = "/savegroup", method = RequestMethod.POST)
    public ResponseEntity saveGroup(@RequestBody String groupData) {
        Group group = new GsonBuilder()
                .create()
                .fromJson(groupData, Group.class);
        groupService.saveGroup(group);
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

    @RequestMapping(value = "/interviewlist", method = RequestMethod.GET)
    public ModelAndView interviewListPage(ModelAndView model) {
        model.addObject("interviewlist", interviewList());
        model.setViewName("/interviewlist");
        return model;
    }

    @RequestMapping(value = "/interview/list", method = RequestMethod.POST)
    @ResponseBody
    public String interviewList() {
        return new Gson().toJson(interviewService.findAll());
    }

    @RequestMapping(value = "/editinterview/{id}", method = RequestMethod.GET)
    public ModelAndView editinterviewPage(@PathVariable Long id, ModelAndView model) {
        model.addObject("interviewdata", interviewService.findOne(id));
        model.setViewName("/editinterview");
        return model;
    }

    @RequestMapping(value = "/interview/new", method = RequestMethod.POST)
    public ResponseEntity createNewInterview(@RequestBody String interviewTitle) {
        Interview interview = new Interview().setTitle(interviewTitle);
        interviewService.save(interview);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/interview/delete", method = RequestMethod.POST)
    public ResponseEntity deleteInterview(@RequestBody Long interviewId) {
        interviewService.delete(interviewId);
        return ResponseEntity.ok().build();
    }

}
