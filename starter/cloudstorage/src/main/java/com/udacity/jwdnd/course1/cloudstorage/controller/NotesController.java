package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.ResultService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotesController {

    private NotesService notesService;
    private ResultService resultService;
    private UserService userService;

    public NotesController(NotesService notesService,UserService userService,ResultService resultService){
        this.notesService = notesService;
        this.resultService = resultService;
        this.userService = userService;
    }

    @PostMapping("/deleteNotes")
    String deleteNotes(@ModelAttribute("noteToDelete") Notes noteToDelete, Authentication authentication, Model resultModel){
        System.out.println(noteToDelete.getNotedescription());
        int notesDeleted = this.notesService.deleteNotes(noteToDelete.getNoteid());
        resultModel = resultService.createResultModel(notesDeleted,resultModel);
        return "result";
    }

    @PostMapping("/addEditNote")
    String addOrEditNotes(@ModelAttribute("addOrEditNotes") Notes newNotes, Model resultModel, Authentication authentication){

        newNotes.setUserid(userService.getUserID(authentication));
        int rowAdded =0;
        if(newNotes.getNoteid() == null)
            rowAdded = notesService.addNotes(newNotes);
        else
            rowAdded = notesService.updateNotes(newNotes);

        resultModel = resultService.createResultModel(rowAdded,resultModel);
        return "result";
    }
}
