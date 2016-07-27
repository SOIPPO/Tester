package org.soippo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.soippo.entity.User;
import org.soippo.exceptions.UserValidationException;
import org.soippo.service.GroupService;
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
import java.io.IOException;

@Controller
public class LoginController {
    @Resource
    private UserService userService;
    @Resource
    private GroupService groupService;

    private FilterProvider excludeUsersFilter = new SimpleFilterProvider()
            .addFilter("excludeUsers", SimpleBeanPropertyFilter.serializeAllExcept("users"));

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerPage(ModelAndView model) {
        model.addObject("grouplist", groupListInJson());
        model.setViewName("/register");
        return model;
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody String userData) throws IOException {
        try {
            return ResponseEntity.ok(new ObjectMapper()
                    .writer(excludeUsersFilter)
                    .writeValueAsString(userService
                            .saveUser(new ObjectMapper()
                                    .readValue(userData, User.class))));
        } catch (UserValidationException ex) {
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(ex.getErrorCode()));
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(ModelAndView model) {
        model.addObject("grouplist", groupListInJson());
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
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping("/login-success")
    @ResponseBody
    public String loginSuccess() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString("");
    }

    @RequestMapping("/login-error")
    public ResponseEntity loginError() throws JsonProcessingException {
        return ResponseEntity.badRequest()
                .body(new ObjectMapper()
                        .writeValueAsString(new BadCredentialsException("Incorrect password!").getMessage()));
    }

    private String groupListInJson() {
        String groupList = null;
        try {
            groupList = new ObjectMapper().writer(excludeUsersFilter).writeValueAsString(groupService.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return groupList;
    }
}