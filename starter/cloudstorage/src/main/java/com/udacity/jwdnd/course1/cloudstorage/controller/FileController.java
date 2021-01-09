package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.ResultService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class FileController {

    FileService fileService;
    ResultService resultService;

    public FileController(FileService fileService,ResultService resultService) {
        this.fileService = fileService;
        this.resultService = resultService;
    }

    @PostMapping ("/upload")
    String uploadFile(@RequestParam("fileUpload")MultipartFile uploadedFile,
                      Authentication userAuthentication,
                      Model resultModel) throws IOException {

        int rowsAdded = fileService.insertFileForUser(uploadedFile,userAuthentication);
        resultService.result.setRowsModified(rowsAdded);
        resultModel = resultService.createResultModel(resultService.result, resultModel);
        return "result";
    }

    @GetMapping("/download")
    ResponseEntity downloadFile(@RequestParam("fileId") Integer fileId){

        File fileToDownload = fileService.fetchFile(fileId);

        return ResponseEntity.ok().
                contentType(MediaType.parseMediaType(fileToDownload.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToDownload.getFilename() + "\"")
                .body(fileToDownload.getFiledata());
    }

    @GetMapping("/delete")
    String deleteFile(@RequestParam("fileId") Integer fileId, Model resultModel){

        int rowDeleted = fileService.deleteFile(fileId);
        resultService.result.setRowsModified(rowDeleted);

        resultModel = resultService.createResultModel(resultService.result,resultModel);
        return "result";
    }

}
