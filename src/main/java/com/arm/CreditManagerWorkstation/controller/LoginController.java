package com.arm.CreditManagerWorkstation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @GetMapping("/login")
    public ModelAndView returnLogin() {
        return new ModelAndView("login");
    }
}