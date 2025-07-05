package com.clinic.appointment.model;

import com.clinic.appointment.model.constant.FileType;
import jakarta.persistence.*;
import lombok.Data;

@Table
@Data
@Entity
public class FileStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    private FileType type;
    private Long fileId;
    private String fileName;
    private String key;
    private String contentType;
    private long fileSize;
    private String serviceName;
}
