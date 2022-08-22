package com.arm.CreditManagerWorkstation.controller;

import com.arm.CreditManagerWorkstation.model.Request;
import com.arm.CreditManagerWorkstation.model.Solution;
import com.arm.CreditManagerWorkstation.model.Type;
import com.arm.CreditManagerWorkstation.model.User;
import com.arm.CreditManagerWorkstation.repository.RequestRepository;
import com.arm.CreditManagerWorkstation.repository.SolutionRepository;
import com.arm.CreditManagerWorkstation.repository.TypeRepository;
import com.arm.CreditManagerWorkstation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class RequestController {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SolutionRepository solutionRepository;
    @Autowired
    DaoAuthenticationProvider authenticationProvider;

    private List<Type> getTypes() {
        return typeRepository.findByType("MARITAL_STATUS");
    }

    private String generateRandomString() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("_", "").replaceAll("-", "");
    }

    @GetMapping("/request/{requestId}")
    public ModelAndView showCreateRequestForm (@PathVariable Long requestId, Model model) {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();

        if(auth.getPrincipal() == "anonymousUser") {
            return new ModelAndView("index");
        }
        String userName = auth.getName();

        Request request = requestRepository.findById(requestId);

        if(request != null && (Objects.equals(request.getUser().getLogin(), userName) || userRepository.hasAuthority(auth, "ADMIN"))) {
            model.addAttribute("request", request);
            return new ModelAndView("request-view");
        }

        return new ModelAndView("index");
    }

    @GetMapping("/create-request")
    public ModelAndView showCreateRequestForm (Model model) {
        Request request = new Request();
        request.setUser(new User());
        request.setMarital_status(new Type());
        model.addAttribute("types", getTypes());
        model.addAttribute("request", request);
        return new ModelAndView("request-creation");
    }

    @RequestMapping(value = "/create-request", method=RequestMethod.POST)
    public ModelAndView processForm(@ModelAttribute(value="request") @Validated Request request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", getTypes());
            return new ModelAndView("request-creation");
        } else {
            //creating new User instance, saving to DB and logging it in.
            User newUser = new User();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String login = generateRandomString();
            String password = generateRandomString();

            newUser.setLogin(login);
            newUser.setPassword(encoder.encode(password));
            newUser.setType("USER");
            userRepository.save(newUser);
            request.setUser(newUser);

            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(login, password);
            Authentication auth = authenticationProvider.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            //saving Request instance in DB
            requestRepository.save(request);

            //creating new Solution instance and saving to DB.
            Solution solution = new Solution();
            solution.setRequest(request);
            solutionRepository.save(solution);

            model.addAttribute("login", login);
            model.addAttribute("password", password);
            model.addAttribute("request", request);

            return new ModelAndView("request-success");
        }
    }
}
