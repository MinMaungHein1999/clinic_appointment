package com.clinic.appointment.config;

import com.clinic.appointment.service.CustomAuthenticationFailureHandler;
import com.clinic.appointment.service.CustomAuthenticationSuccessHandler;
import com.clinic.appointment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder; // inject password Encoder
    @Autowired
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/confirm-account", "/static/assets/**").permitAll()
                        .requestMatchers("/select-role").authenticated()
                        .requestMatchers("/set-active-role").authenticated()
                .anyRequest().authenticated()).formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"));
        return http.build();
    }
}
