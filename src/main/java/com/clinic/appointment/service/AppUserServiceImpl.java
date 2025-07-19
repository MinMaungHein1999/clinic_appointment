package com.clinic.appointment.service;

import com.amazonaws.services.kms.model.DisabledException;
import com.clinic.appointment.expection.AccountNotConfrimedException;
import com.clinic.appointment.model.AppUser;
import com.clinic.appointment.model.Role;
import com.clinic.appointment.repository.AppUserRepository;
import com.clinic.appointment.repository.RoleRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements UserService{

    @Autowired
    private final AppUserRepository appUserRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not fount with email: "+ email));
        if(!user.isAccountConfirmed()){
            throw new AccountNotConfrimedException("account-not-confrimed");
        }
        ServletRequestAttributes attr  = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        session.setAttribute("currentUser", user);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public AppUser registerNewUser(AppUser user) {
        if(appUserRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username already exists!");
        }
        if(appUserRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName("USER").orElseGet(()-> {
            Role newRole = new Role();
            newRole.setName("USER");
            return roleRepository.save(newRole);
        });
        user.setRoles(Collections.singleton(userRole));

        return appUserRepository.save(user);
    }
}
