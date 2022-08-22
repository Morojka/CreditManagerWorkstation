package com.arm.CreditManagerWorkstation.controller;

import com.arm.CreditManagerWorkstation.model.Request;
import com.arm.CreditManagerWorkstation.model.Type;
import com.arm.CreditManagerWorkstation.repository.RequestRepository;
import com.arm.CreditManagerWorkstation.repository.TypeRepository;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
public class RequestController {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    TypeRepository typeRepository;

    @GetMapping("/create-request")
    public ModelAndView showCreateRequestForm (Model model) {
        List<Type> types = typeRepository.findByType("MARITAL_STATUS");
        Request rq = new Request();
        rq.setMarital_status(new Type());
        model.addAttribute("types", types);
        model.addAttribute("request", rq);
        return new ModelAndView("request-creation");
    }

    @RequestMapping(value = "/create-request-post", method=RequestMethod.POST)
    public String processForm(@ModelAttribute(value="request") Request request) {
        requestRepository.save(request);
        return "success";
    }
}
