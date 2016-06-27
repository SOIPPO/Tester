package org.soippo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.soippo.entity.User;
import org.soippo.exceptions.UserValidationException;
import org.soippo.serialization.GroupWithoutUserlistSerializer;
import org.soippo.serialization.UserDeserializer;
import org.soippo.serialization.UserSerializer;
import org.soippo.service.SerializeService;
import org.soippo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Resource
    private UserService userService;
    @Resource
    private SerializeService serializeService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerPage(ModelAndView model) {
        model.addObject("grouplist", serializeService.serializeGroup(new UserSerializer(), new GroupWithoutUserlistSerializer()));
        model.setViewName("/register");
        return model;
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(ModelAndView model) {
        model.addObject("grouplist", serializeService.serializeGroup(new UserSerializer(), new GroupWithoutUserlistSerializer()));
        model.setViewName("/login");
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    String login(Model model, @RequestParam(required = false) String message) {
        model.addAttribute("message", message);
        return "messages";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping("/login-success")
    @ResponseBody
    public String loginSuccess() {
        return new Gson().toJson("");
    }

    @RequestMapping("/login-error")
    public ResponseEntity loginError() {
        return ResponseEntity.badRequest()
                .body(new Gson().toJson(new BadCredentialsException("Incorrect password!").getMessage()));
    }
}