package com.clinic.appointment.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.clinic.appointment.model.FileStorage;
import com.clinic.appointment.model.constant.FileType;
import com.clinic.appointment.repository.FileStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${cloud.aws.bucket}")
    private String s3BucketName;
    @Value("${cloud.aws.region.static}")
    private String awsRegion;
    private String s3BaseUrl="https://%s.s3.%s.amazonaws.com/%s";

    private final FileStorageRepository fileStorageRepository;
    private final AmazonS3 amazonS3;

    public String getFileName(FileType fileType, Long id){
        List<FileStorage> fileStorageList = fileStorageRepository.findByTypeAndFileId(fileType, id);
        FileStorage file = fileStorageList.getFirst();
        return getFileUrl(file.getKey(),file.getServiceName());
    }

    public String getFileUrl(String fileKey, String serviceName){
        if("local".equalsIgnoreCase(serviceName)){
            return "/files/"+fileKey;
        }else{
            return String.format(s3BaseUrl, s3BucketName, awsRegion, fileKey);
        }

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
            if("local".equalsIgnoreCase(serviceName)) {
                File dir = new File(UPLOAD_DIR);
                if (!dir.exists()) dir.mkdirs();
                String filePath = UPLOAD_DIR + storedFileName;
                file.transferTo(new File(filePath));
                System.out.println("File uploaded Successfull to Local : "+ storedFileName);
            }else{
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());
                amazonS3.putObject(s3BucketName, storedFileName, file.getInputStream(), metadata);
                log.info("Filde Upload Successfull to S3 : {}", storedFileName);
            }

            FileStorage fileStorage = new FileStorage();
            fileStorage.setFileName(file.getOriginalFilename());
            fileStorage.setKey(storedFileName);
            fileStorage.setFileSize(file.getSize());
            fileStorage.setType(fileType);
            fileStorage.setFileId(id);
            fileStorage.setContentType(file.getContentType());
            fileStorage.setServiceName(serviceName);

            fileStorageRepository.save(fileStorage);


        }catch (IOException ex){
            throw new RuntimeException("Failed to upload : "+ ex.getMessage());
        }

    }
}
