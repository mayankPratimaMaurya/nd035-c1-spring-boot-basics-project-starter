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
    String loadSignUpPage(){
        return "signup";
    }

    @PostMapping()
    String createUserForCloudStorage(@ModelAttribute User user, Model model){

        boolean isSignUpSucess= false;
        boolean isSignUpError = false;

        if(userService.isUserNameAvailable(user.getUsername()))
            isSignUpError= true;

        if (!isSignUpError) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded > 0)
                isSignUpSucess = true;
        }

        model.addAttribute("isSignUpSucess",isSignUpSucess);
        model.addAttribute("isSignUpError",isSignUpError);

        return "signup";
    }
}
