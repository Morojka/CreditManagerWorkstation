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

// TODO: 21.08.2022 -определить даопровайдер в файл конфигурации авторизации
// TODO: 21.08.2022 -создать репозиторий пользователя, определить в нем работу с базой( оплучение, и т.д.)
// TODO: 21.08.2022 -модель пользователя (должен переопределять UserDetails)
// TODO: 21.08.2022 -определить кодировщик пароля в виде bean'a (bcrypt) в файл конфигурации авторизации
