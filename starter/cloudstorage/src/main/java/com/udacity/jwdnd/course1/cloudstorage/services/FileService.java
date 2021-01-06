package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        File file = getFileObjectForUser(uploadedFile,userAuthentication);
        return fileMapper.insertFile(file);
    }

    public List<File> getFileMetaData(int userid){
        return fileMapper.getFileMetaData(userid);
    }

    private File getFileObjectForUser(MultipartFile uploadedFile, Authentication userAuthentication) throws IOException{

        File file = new File();
        file.setFiledata(uploadedFile.getBytes());
        file.setFilesize(Long.toString( uploadedFile.getSize()));
        file.setContenttype(uploadedFile.getContentType());
        file.setFilename(uploadedFile.getOriginalFilename());
        file.setUserid(userService.getUserID(userAuthentication));
        return file;
    }
}
