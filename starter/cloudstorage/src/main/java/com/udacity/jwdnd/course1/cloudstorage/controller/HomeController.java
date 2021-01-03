package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    String loadHomePage(Model model){

        List<Notes> cloudStorageNotes = new ArrayList<>();
        cloudStorageNotes.add(new Notes(1, "test", "test descrption", 123));
        cloudStorageNotes.add(new Notes(2, "test1", "test descrption", 12));
        cloudStorageNotes.add(new Notes(3, "test2", "test descrption", 14));
        cloudStorageNotes.add(new Notes(4, "test3", "test descrption", 15));
        model.addAttribute("cloudStorageNotes",cloudStorageNotes);
        return "home";
    }

    @PostMapping
    String deleteUser(@ModelAttribute("noteToDelete") Notes noteToDelete, Model model){
        List<Notes> cloudStorageNotes = new ArrayList<>();
        cloudStorageNotes.add(new Notes(1, "test111", "test descrption", 123));
        cloudStorageNotes.add(new Notes(2, "test1222", "test descrption", 12));
        cloudStorageNotes.add(new Notes(3, "test2333", "test descrption", 14));
        cloudStorageNotes.add(new Notes(4, "test3444", "test descrption", 15));
        model.addAttribute("cloudStorageNotes",cloudStorageNotes);
        return "home";
    }
}
