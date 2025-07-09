package com.clinic.appointment.config;


import com.clinic.appointment.service.ActiveRoleService;
import com.clinic.appointment.service.CustomAuthenticationFailureHandler;
import com.clinic.appointment.service.CustomAuthenticationSuccessHandler;
import com.clinic.appointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private ActiveRoleService activeRoleService;

    @PostConstruct
    public void checkActiveRoleServiceInjection() {
        if (activeRoleService == null) {
            System.out.println("ALERT: ActiveRoleService was NOT injected! It is null.");
        } else {
            boolean result = activeRoleService.hasActiveRole("ROLE_ADMIN");
            System.out.println("ActiveRoleService Called : " + result);
            System.out.println("ActiveRoleService was successfully injected.");
        }
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    /**
     * Defines a custom DefaultHttpSecurityExpressionHandler.
     * This is crucial for allowing SpEL expressions to resolve Spring beans
     * (like @activeRoleService) from the ApplicationContext in Spring Security 6+.
     */
    @Bean
    public DefaultHttpSecurityExpressionHandler httpSecurityExpressionHandler() {
        DefaultHttpSecurityExpressionHandler expressionHandler = new DefaultHttpSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext); // Set the application context
        return expressionHandler;
    }

    /**
     * Helper method to create WebExpressionAuthorizationManager with the custom
     * DefaultHttpSecurityExpressionHandler.
     * The httpSecurityExpressionHandler is now passed as a method argument,
     * which Spring will inject because it's a known @Bean. This breaks the cycle.
     */
    private WebExpressionAuthorizationManager createWebExpressionAuthorizationManager(
            String expression,
            DefaultHttpSecurityExpressionHandler expressionHandler // INJECTED AS PARAMETER
    ) {
        WebExpressionAuthorizationManager manager = new WebExpressionAuthorizationManager(expression);
        manager.setExpressionHandler(expressionHandler); // Use the injected handler
        return manager;
    }

    /**
     * Configures the security filter chain for HTTP requests.
     * Defines authorization rules, form login, logout, and exception handling.
     * @param http The HttpSecurity object to configure.
     * @return The built SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Spring will automatically inject the `httpSecurityExpressionHandler` bean
        // into this method because it's available in the context.
        DefaultHttpSecurityExpressionHandler currentExpressionHandler = httpSecurityExpressionHandler(); // Call the @Bean method directly

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/confirm-account/otp**","/confirm-account/verify-otp**", "/static/assets/**").permitAll()
                        .requestMatchers("/select-role", "/set-active-role").authenticated()
                        .requestMatchers("/admins/**").access(createWebExpressionAuthorizationManager("hasRole('ROLE_ADMIN') and @activeRoleService.hasActiveRole('ROLE_ADMIN')", currentExpressionHandler))
                        .requestMatchers("/doctors/**").access(createWebExpressionAuthorizationManager("hasRole('ROLE_DOCTOR') and @activeRoleService.hasActiveRole('ROLE_DOCTOR')", currentExpressionHandler))
                        .requestMatchers("/patients/**").access(createWebExpressionAuthorizationManager("hasRole('ROLE_PATIENT') and @activeRoleService.hasActiveRole('ROLE_PATIENT')", currentExpressionHandler))
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successHandler(customAuthenticationSuccessSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"));

        return http.build();
    }
}