package org.soippo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.soippo.entity.User;
import org.soippo.exceptions.UserValidationException;
import org.soippo.service.GroupService;
import org.soippo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    private ObjectMapper objectMapper = new ObjectMapper()
            .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerPage(ModelAndView model) {
        model.addObject("grouplist", groupListInJson());
        model.setViewName("register");
        return model;
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody String userData) throws IOException {
        try {
            return ResponseEntity.ok(objectMapper
                    .writeValueAsString(userService
                            .saveUser(objectMapper
                                    .readValue(userData, User.class))));
        } catch (UserValidationException ex) {
            return ResponseEntity.badRequest().body(objectMapper.writeValueAsString(ex.getErrorCode()));
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(ModelAndView model) {
        model.addObject("grouplist", groupListInJson());
        model.setViewName("login");
        return model;
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
        return objectMapper.writeValueAsString("");
    }

    @RequestMapping("/login-error")
    public ResponseEntity loginError() throws JsonProcessingException {
        return ResponseEntity.badRequest()
                .body(objectMapper
                        .writeValueAsString(new BadCredentialsException("Incorrect password!").getMessage()));
    }

    private String groupListInJson() {
        String groupList = null;
        try {
            groupList = objectMapper
                    .writeValueAsString(groupService.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return groupList;
    }
}