package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private UserService userService;
    private NotesService notesService;
    private CredentialService credentialsService;

    public HomeService(UserService userService, NotesService notesService, CredentialService credentialsService) {
        this.userService = userService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
    }


    public List<Notes> cloudStorageNotesForUser(Authentication authentication ){
        return notesService.getNotesListForUser(getLoggedInUserId(authentication));
    }

    public List<Credentials> cloudStorageCredentialsForUser(Authentication authentication ){
        return credentialsService.getAllCredentialsForUser(getLoggedInUserId(authentication));
    }

    private int getLoggedInUserId(Authentication authentication) {
        int loggedInUserId = userService.getUser(authentication.getName()).getUserid();
        return loggedInUserId;
    }


}
