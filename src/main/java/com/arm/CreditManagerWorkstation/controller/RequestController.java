package com.arm.CreditManagerWorkstation.controller;

import com.arm.CreditManagerWorkstation.model.Request;
import com.arm.CreditManagerWorkstation.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @GetMapping("/create-request")
    public ModelAndView showCreateRequestForm () {
        return new ModelAndView("request-creation");
    }

    @PostMapping("/create-request")
    public Request create(@RequestBody Request request) {
        return requestRepository.save(request);
    }
}
