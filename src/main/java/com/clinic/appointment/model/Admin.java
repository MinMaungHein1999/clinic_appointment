package com.clinic.appointment.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table(name = "admins")
public class Admin extends UserMasterData{
}
