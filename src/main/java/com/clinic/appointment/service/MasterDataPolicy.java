package com.clinic.appointment.service;

import com.clinic.appointment.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterDataPolicy {
    @Autowired
    private AuthService authService;

    private Long getCurrentUserId(){
      AppUser appUser = authService.getCurrentUser();
      return appUser.getId();
    }

    public boolean isAdmin(){
        return this.getActiveRole().equalsIgnoreCase("ROLE_ADMIN");
    }

    private String getActiveRole(){
        return authService.getActiveRole();
    }

    public boolean canView(MasterData masterData){
        return isAuthenticated();
    }

    public boolean canDelete(MasterData entity){
        if(entity.isDeleted()){
            return false;
        }

        if(entity.isAdmin() || entity.isPatient() || entity.isDoctor() || entity.isAppUser()){
            if(!this.isAdmin()){
                return false;
            }
        }

        return entity.isOwned(this.getCurrentUserId());
    }

    public boolean canUpdate(MasterData entity){
        if(entity.isDeleted()){
            return false;
        }

        return entity.isOwned(this.getCurrentUserId());
    }

    public boolean canCreate(MasterData entity){
        if(entity.isDoctor() && !isAdmin()){
            return false;
        }

        if(entity.isAdmin() && !isAdmin()){
            return false;
        }

       return isAuthenticated();
    }

    public boolean isAuthenticated(){
        Long currentUserId = this.getCurrentUserId();
        return currentUserId != null;
    }
}
