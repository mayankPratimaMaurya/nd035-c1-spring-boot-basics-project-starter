package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private UserService userService;
    private NotesService notesService;
    private CredentialService credentialsService;
    private FileService filesService;

    public HomeService(UserService userService, NotesService notesService, CredentialService credentialsService, FileService filesService) {
        this.userService = userService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.filesService = filesService;
    }

    public List<Notes> cloudStorageNotesForUser(Authentication authentication ){
        int loggedInUserId = userService.getUserID(authentication);
        return notesService.getNotesListForUser(loggedInUserId);
    }

    public List<Credentials> cloudStorageCredentialsForUser(Authentication authentication ){
        int loggedInUserId = userService.getUserID(authentication);
        return credentialsService.getAllCredentialsForUser(loggedInUserId);
    }

    public List<File> cloudStorageFilesForUser(Authentication authentication ){
        int loggedInUserId = userService.getUserID(authentication);
        return filesService.getFileMetaData(loggedInUserId);
    }

}
