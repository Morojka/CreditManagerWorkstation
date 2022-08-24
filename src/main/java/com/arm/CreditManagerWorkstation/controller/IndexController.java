package com.arm.CreditManagerWorkstation.controller;

import com.arm.CreditManagerWorkstation.model.Request;
import com.arm.CreditManagerWorkstation.model.User;
import com.arm.CreditManagerWorkstation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class IndexController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        if (userService.hasAuthority("USER")) {
            User user = userService.getUserFromSession();
            Request request = user.getRequest();
            model.addAttribute("request", request);
        }

        return new ModelAndView("index");
    }
}
