package com.arm.CreditManagerWorkstation.controller;

import com.arm.CreditManagerWorkstation.model.Request;
import com.arm.CreditManagerWorkstation.model.User;
import com.arm.CreditManagerWorkstation.repository.RequestRepository;
import com.arm.CreditManagerWorkstation.repository.TypeRepository;
import com.arm.CreditManagerWorkstation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@RestController
public class IndexController {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public ModelAndView index (Model model) {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();

        if(userRepository.hasAuthority(auth, "USER")) {
            User user = userRepository.findByLogin(auth.getName());
            Request request = user.getRequest();
            model.addAttribute("request", request);
        }

        return new ModelAndView("index");
    }
}
