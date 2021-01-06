package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private HomeService homeService;

    public HomeController(HomeService homeService){
        this.homeService = homeService;
    }

    @GetMapping("/home")
    String loadHomePage(@ModelAttribute("addOrEditNotes") Notes addOrEditNotes,
                        @ModelAttribute("addOrEditCredentials") Credentials addOrEditCredentials,
                        Authentication authentication,
                        Model model){

        model.addAttribute("cloudStorageNotes",homeService.cloudStorageNotesForUser(authentication));
        model.addAttribute("cloudStorageCredentials",homeService.cloudStorageCredentialsForUser(authentication));
        model.addAttribute("cloudStorageFiles",homeService.cloudStorageFilesForUser(authentication));

        return "home";
    }

}
