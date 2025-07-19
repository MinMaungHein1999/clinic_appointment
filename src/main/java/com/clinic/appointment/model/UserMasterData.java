package com.clinic.appointment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public abstract  class UserMasterData extends MasterData {
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "ID" )
    @JsonIgnore
    private AppUser appUser;
}
