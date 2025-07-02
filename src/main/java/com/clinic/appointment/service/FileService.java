package com.clinic.appointment.service;

import com.clinic.appointment.model.FileStorage;
import com.clinic.appointment.model.constant.FileType;
import com.clinic.appointment.repository.FileStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private static final String UPLOAD_DIR = "/Users/minmaunghein/documents/clinic_management_system/";
    private final FileStorageRepository fileStorageRepository;

    public String getFileName(FileType fileType, Long id){
        List<FileStorage> fileStorageList = fileStorageRepository.findByTypeAndFileId(fileType, id);
        return getFileUrl(fileStorageList.getFirst().getKey());
    }

    public String getFileUrl(String fileKey){
        return "/files/"+fileKey;
    }

    public void handleFileUpload(MultipartFile file, FileType fileType, Long id, String serviceName){

        if(file.isEmpty()){
            throw new RuntimeException("Please select a file to upload.");
        }

        String originalFileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String storedFileName = uuid + fileExtension;

        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();
            String filePath = UPLOAD_DIR + storedFileName;
            file.transferTo(new File(filePath));

            FileStorage fileStorage = new FileStorage();
            fileStorage.setFileName(file.getOriginalFilename());
            fileStorage.setKey(storedFileName);
            fileStorage.setFileSize(file.getSize());
            fileStorage.setType(fileType);
            fileStorage.setFileId(id);
            fileStorage.setContentType(file.getContentType());
            fileStorage.setServiceName(serviceName);

            fileStorageRepository.save(fileStorage);

            System.out.println("File uploaded Successfully : "+ filePath);
        }catch (IOException ex){
            throw new RuntimeException("Failed to upload : "+ ex.getMessage());
        }

    }
}
