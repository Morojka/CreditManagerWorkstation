package com.arm.CreditManagerWorkstation.service;

import com.arm.CreditManagerWorkstation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final com.arm.CreditManagerWorkstation.model.User customer = userRepository.findByLogin(login);
        if (customer == null) {
            throw new UsernameNotFoundException(login);
        }

        return User.withUsername(customer.getLogin())
                .password(customer.getPassword())
                .authorities("USER").build();
    }
}