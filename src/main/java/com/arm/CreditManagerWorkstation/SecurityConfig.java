package com.arm.CreditManagerWorkstation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        return provider;
//    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login.html")
//                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index.html", true);
    }

//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                );
//        return null;
//    }
}