package com.clinic.appointment.service;

import com.clinic.appointment.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    AppUser registerNewUser(AppUser user);
}
