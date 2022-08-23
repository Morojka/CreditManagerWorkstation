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
import java.sql.Date;
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

    private List<Type> getTypes(String type) {
        return typeRepository.findByType(type);
    }

    private String generateRandomString() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("_", "").replaceAll("-", "");
    }

    @GetMapping("/create-request")
    public ModelAndView showCreateRequestForm (Model model) {
        Request request = new Request();
        request.setUser(new User());
        request.setMarital_status(new Type());
        model.addAttribute("types", getTypes("MARITAL_STATUS"));
        model.addAttribute("request", request);
        return new ModelAndView("request-creation");
    }

    @RequestMapping(value = "/create-request", method=RequestMethod.POST)
    public ModelAndView processForm(@ModelAttribute(value="request") @Validated Request request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", getTypes("MARITAL_STATUS"));
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

    @GetMapping("/request/{requestId}")
    public ModelAndView showRequest (@PathVariable Long requestId, Model model) {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();

        if(auth.getPrincipal() == "anonymousUser") {
            return new ModelAndView("redirect:/");
        }
        String userName = auth.getName();

        Request request = requestRepository.findById(requestId);

        if(request != null && (Objects.equals(request.getUser().getLogin(), userName) || userRepository.hasAuthority(auth, "ADMIN"))) {
            model.addAttribute("request", request);
            return new ModelAndView("request-view");
        }

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/sign-request/{requestId}", method=RequestMethod.POST)
    public ModelAndView signRequest(@PathVariable Long requestId) {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();

        if(auth.getPrincipal() == "anonymousUser") {
            return new ModelAndView("redirect:/");
        }
        String userName = auth.getName();

        Request request = requestRepository.findById(requestId);
        Solution solution = solutionRepository.findById(request.getSolution().getId());

        if(solution != null && Objects.equals(request.getUser().getLogin(), userName)) {
//            System.out.println(solution);
            solution.setSign_date(new Date(System.currentTimeMillis()));
            solution.setSigned(Boolean.TRUE);
            solutionRepository.update(solution);
        }
        return new ModelAndView("redirect:/request/" + requestId);
    }

    @GetMapping("/admin/requests")
    public ModelAndView showRequests(Model model) {
        List<Request> requests = requestRepository.getAll();

        model.addAttribute("requests", requests);
        return new ModelAndView("admin/requests");
    }

    @GetMapping("/admin/clients")
    public ModelAndView showClients(Model model) {
        List<Request> requests = requestRepository.getAll();
        model.addAttribute("requests", requests);
        model.addAttribute("types", getTypes("SEARCH_TYPE"));
        return new ModelAndView("admin/clients");
    }

    @GetMapping("/admin/clients/search")
    public ModelAndView showSearchClients(@RequestParam String query, @RequestParam Long searchTypeId, Model model) {
        List<Request> requests = requestRepository.getAll();
        List<Type> searchTypes = getTypes("SEARCH_TYPE");
        model.addAttribute("types", searchTypes);

        Type searchType = searchTypes.stream().filter(type -> type.getId().equals(searchTypeId)).findAny().orElse(null);
        if(searchType == null) {
            model.addAttribute("requests", requests);
            return new ModelAndView("redirect:/admin/clients");
        }

        switch (searchType.getName()) {
            case "Ф.И.О." -> requests = requestRepository.searchByName(query);
            case "Паспорт" -> requests = requestRepository.searchByPassport(query);
            case "Телефон" -> requests = requestRepository.searchByPhone(query);
        }

        model.addAttribute("prevType", searchTypeId);
        model.addAttribute("prevQuery", query);
        model.addAttribute("requests", requests);
        return new ModelAndView("admin/clients");
    }
}