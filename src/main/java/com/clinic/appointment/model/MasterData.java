package com.clinic.appointment.model;

import com.clinic.appointment.model.constant.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract  class MasterData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Status status = Status.ACTIVE;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crated_by_id", referencedColumnName = "ID" )
    @JsonIgnore
    private AppUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id", referencedColumnName = "ID" )
    @JsonIgnore
    private AppUser updatedBy;

    @JsonIgnore
    public boolean isDeleted(){
        return this.status == Status.DELETE;
    }

    @JsonIgnore
    public boolean isOwned(Long id){
        return id != null && this.getCreatedBy() != null && this.getCreatedBy().getId() == id;
    }

    @JsonIgnore
    public boolean isAdmin(){
        return this instanceof Admin;
    }

    @JsonIgnore
    public boolean isPatient(){
        return this instanceof Patient;
    }

    @JsonIgnore
    public boolean isDoctor(){
        return this instanceof Doctor;
    }

    @JsonIgnore
    public boolean isAppUser(){
        return this instanceof AppUser;
    }

    @JsonIgnore
    public void delete(){
        this.status = Status.DELETE;
    }
}
