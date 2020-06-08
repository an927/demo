package com.fizzdev.demo.controller;

import com.fizzdev.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.concurrent.TimeUnit;

@Controller
@SessionAttributes("username")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showUserPage(ModelMap model) {
        model.put("allUsers", userService.fetchAllUsers());

        return "user";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addUser(ModelMap model,
                          @RequestParam(value = "username", required = true) final String userName,
                          @RequestParam(value = "firstname", required = true) final String firstName,
                          @RequestParam(value = "lastname", required = true) final String lastName,
                          @RequestParam(value = "birth", required = true) final String birth) throws InterruptedException {

        if (!userService.queueUser(userName, firstName, lastName, birth)) {
            model.put("errorMessage", "Invalid Credentials");
        }

        TimeUnit.SECONDS.sleep(2);
        model.put("allUsers", userService.fetchAllUsers());

        return "user";
    }

}
