package com.arm.CreditManagerWorkstation.controller;

import com.arm.CreditManagerWorkstation.model.Request;
import com.arm.CreditManagerWorkstation.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class SolutionController {

    @Autowired
    RequestRepository requestRepository;

    @GetMapping("/admin/solutions")
    public ModelAndView showSolutions(Model model) {
        List<Request> requests = requestRepository.whereHasSolution(Boolean.FALSE);

        model.addAttribute("requests", requests);
        return new ModelAndView("admin/solutions");
    }
}
