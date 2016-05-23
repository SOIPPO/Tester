package org.soippo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.soippo.entity.User;
import org.soippo.exceptions.UserValidationException;
import org.soippo.service.SerializeService;
import org.soippo.service.UserService;
import org.soippo.utils.GroupWithoutUserlistSerializer;
import org.soippo.utils.UserDeserializer;
import org.soippo.utils.UserSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class LoginController {
    @Resource
    private UserService userService;
    @Resource
    private SerializeService serializeService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerPage(ModelAndView model) {
        model.addObject("grouplist", serializeService.serializeGroup(new UserSerializer(), new GroupWithoutUserlistSerializer()));
        model.setViewName("register");
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
}
