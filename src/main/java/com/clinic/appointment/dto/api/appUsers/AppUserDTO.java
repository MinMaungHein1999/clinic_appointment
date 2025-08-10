package com.clinic.appointment.dto.api.appUsers;

import com.clinic.appointment.model.AppUser;
import com.clinic.appointment.model.Role;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AppUserDTO {
    private Long id;
    private String username;
    private String email;
    private Boolean isAccountConfrimed;
    private List<Role> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AppUser createdBy;
    private AppUser updatedBy;
}
