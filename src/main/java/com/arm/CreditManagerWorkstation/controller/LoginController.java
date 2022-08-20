package com.arm.CreditManagerWorkstation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class LoginController {
    @GetMapping("/login")
    public ModelAndView returnLogin () {
        return new ModelAndView("login");
    }
    @PostMapping("/login")
    public Map<String, String> login (@RequestParam Map<String,String> allParams) {
        return allParams;
//    public ModelAndView login (@RequestParam String login, @RequestParam String password) {
//        return new ModelAndView("index");
    }
}
