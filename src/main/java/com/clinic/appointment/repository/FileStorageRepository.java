package com.clinic.appointment.repository;

import com.clinic.appointment.model.FileStorage;
import com.clinic.appointment.model.constant.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
    List<FileStorage> findByTypeAndFileId(FileType type, Long id);
}
