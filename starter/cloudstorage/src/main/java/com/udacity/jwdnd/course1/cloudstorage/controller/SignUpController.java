package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/signup")
public class SignUpController {

    private UserService userService;
    public SignUpController(UserService userService){
        this.userService = userService;

    }
    @GetMapping()
    String loadSignUpPage(@ModelAttribute("userModel") User user){
        return "signup";
    }

    @PostMapping()
    String createUserForCloudStorage(@ModelAttribute("userModel") User user, Model model){

        String signUpError = null;

        if(!userService.isUserNameAvailable(user.getUsername()))
            signUpError = "User Already Exists!!";

        if (signUpError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0)
                signUpError = "Issue in creating User. Please try again";
        }

        if(signUpError == null) {
            model.addAttribute("isSignUpSucess", true);
        }else{
            model.addAttribute("signUpError",signUpError);
        }

        return "signup";
    }
}
