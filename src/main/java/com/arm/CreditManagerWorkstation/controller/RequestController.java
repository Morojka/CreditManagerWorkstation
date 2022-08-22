package com.arm.CreditManagerWorkstation.controller;

import com.arm.CreditManagerWorkstation.model.Request;
import com.arm.CreditManagerWorkstation.model.Type;
import com.arm.CreditManagerWorkstation.repository.RequestRepository;
import com.arm.CreditManagerWorkstation.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
public class RequestController {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    TypeRepository typeRepository;

    private List<Type> getTypes() {
        return typeRepository.findByType("MARITAL_STATUS");
    }

    @GetMapping("/create-request")
    public ModelAndView showCreateRequestForm (Model model) {
        Request rq = new Request();
        rq.setMarital_status(new Type());
        model.addAttribute("types", getTypes());
        model.addAttribute("request", rq);
        return new ModelAndView("request-creation");
    }

    @RequestMapping(value = "/create-request", method=RequestMethod.POST)
    public ModelAndView processForm(@ModelAttribute(value="request") @Validated Request request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", getTypes());
            return new ModelAndView("request-creation");
        } else {
            model.addAttribute("success", true);
            requestRepository.save(request);
            return new ModelAndView("index");
        }
    }
}
