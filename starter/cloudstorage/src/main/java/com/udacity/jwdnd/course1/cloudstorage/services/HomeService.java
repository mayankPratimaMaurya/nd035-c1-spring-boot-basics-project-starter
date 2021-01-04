package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private UserService userService;
    private NotesService notesService;

    public HomeService(NotesService notesService,UserService userService){
        this.notesService = notesService;
        this.userService = userService;
    }

    public List<Notes> cloudStorageNotesForUser(Authentication authentication ){
        int loggedInUserId = userService.getUser(authentication.getName()).getUserid();
        return this.notesService.getNotesListForUser(loggedInUserId);
    }
}
