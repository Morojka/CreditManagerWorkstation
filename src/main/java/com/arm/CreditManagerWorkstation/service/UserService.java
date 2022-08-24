package com.arm.CreditManagerWorkstation.service;

import com.arm.CreditManagerWorkstation.model.User;
import com.arm.CreditManagerWorkstation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DaoAuthenticationProvider authenticationProvider;

    public String generateRandomString() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("_", "").replaceAll("-", "");
    }

    public User saveNewUser(String login, String password, String type) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setLogin(login);
        user.setPassword(encoder.encode(password));
        user.setType(type);

        userRepository.save(user);
        return user;
    }

    public Boolean hasAuthority(String authority) {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();

        return auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(authority));
    }

    public void loginUser(String login, String password) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(login, password);
        Authentication auth = authenticationProvider.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
    }

    public User getUserFromSession(){
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();

        return userRepository.findByLogin(auth.getName());
    }

}
