package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    FileMapper fileMapper;
    UserService userService;

    public FileService(FileMapper fileMapper,UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public int insertFileForUser(MultipartFile uploadedFile, Authentication userAuthentication) throws IOException {
        File file = createFileObjectForUser(uploadedFile,userAuthentication);
        return fileMapper.insertFile(file);
    }

    public List<File> getAllFileMetaDataForUser(int userid){
        return fileMapper.getFileMetaData(userid);
    }

    public File fetchFile(int fileId){
        return fileMapper.fetchFile(fileId);
    }

    public int deleteFile(int fileId){
        return fileMapper.deleteFile(fileId);
    }

    private File createFileObjectForUser(MultipartFile uploadedFile, Authentication userAuthentication) throws IOException{

        File file = new File();
        file.setFiledata(uploadedFile.getBytes());
        file.setFilesize(Long.toString( uploadedFile.getSize()));
        file.setContenttype(uploadedFile.getContentType());
        file.setFilename(uploadedFile.getOriginalFilename());
        file.setUserid(userService.getUserID(userAuthentication));
        return file;
    }

    public String checkIfFileToBeUploadedIsValid(MultipartFile fileToUpload, Authentication userAuthentication) {

        String fileName = fileToUpload.getOriginalFilename();

        if(fileName.isEmpty()){
            return "No file selected. Please select a file to upload.";
        }

        if(isFileDuplicate(userAuthentication, fileName)){
            return "File already exists.";
        }
        return null;
    }

    private boolean isFileDuplicate(Authentication userAuthentication, String fileName) {
        List<File> fileListForUser = getAllFileMetaDataForUser(userService.getUserID(userAuthentication));
        return fileListForUser.stream().anyMatch(file -> file.getFilename().equals(fileName));
    }
}
