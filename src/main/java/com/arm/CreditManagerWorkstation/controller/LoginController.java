package com.arm.CreditManagerWorkstation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @GetMapping("/login")
    public ModelAndView returnLogin (Model model) {
        String login = "";
        String password = "";
        model.addAttribute("login", login);
        model.addAttribute("password", password);
        return new ModelAndView("login");
    }
}