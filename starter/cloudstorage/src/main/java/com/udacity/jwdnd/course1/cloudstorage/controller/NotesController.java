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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    String deleteNotes(@ModelAttribute("noteToDelete") Notes noteToDelete, Authentication authentication, RedirectAttributes resultModel){
        resultService.resultDataRefresh();
        System.out.println(resultService.result.getErrorMessage() + resultService.result.getRowsModified());
        int notesDeleted = this.notesService.deleteNotes(noteToDelete.getNoteid());
        resultService.result.setRowsModified(notesDeleted);
        resultModel = resultService.createResultModel(resultService.result, resultModel);
        return "redirect:/home";
    }

    @PostMapping("/addEditNote")
    String addOrEditNotes(@ModelAttribute("addOrEditNotes") Notes newNotes, RedirectAttributes resultModel, Authentication authentication){

        resultService.resultDataRefresh();
        newNotes.setUserid(userService.getUserID(authentication));
        int rowAdded =0;
        if(newNotes.getNoteid() == null)
            rowAdded = notesService.addNotes(newNotes);
        else
            rowAdded = notesService.updateNotes(newNotes);

        resultService.result.setRowsModified(rowAdded);
        resultModel = resultService.createResultModel(resultService.result, resultModel);
        return "redirect:/home";
    }
}
