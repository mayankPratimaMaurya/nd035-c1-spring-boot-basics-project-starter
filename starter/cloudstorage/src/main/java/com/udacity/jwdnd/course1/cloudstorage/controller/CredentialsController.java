package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.ResultService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialsController {

    CredentialService credentialsService;
    ResultService resultService;
    UserService userService;

    public CredentialsController(CredentialService credentialsService, ResultService resultService, UserService userService) {
        this.credentialsService = credentialsService;
        this.resultService = resultService;
        this.userService = userService;
    }

    @PostMapping("/deleteCredentials")
    String deleteCredentials(@ModelAttribute("credentialsToDelete") Credentials credentialsToDelete, Model resultModel){
        int credentialsDeleted = credentialsService.removeCredentials(credentialsToDelete.getCredentialid());
        resultModel = resultService.createResultModel(credentialsDeleted,resultModel);
        return "result";
    }

    @PostMapping("addEditCredentials")
    String addOrEditCredentials(@ModelAttribute("addOrEditCredentials") Credentials addOrEditCredentials,
                                Authentication authentication,
                                Model resultModel){

        addOrEditCredentials.setUserid(userService.getUserID(authentication));

        int rowAdded=0;
        if(addOrEditCredentials.getCredentialid() == null)
            rowAdded = credentialsService.addNewCredentials(addOrEditCredentials);
        else
            rowAdded = credentialsService.updateCredentials(addOrEditCredentials);

        resultModel = resultService.createResultModel(rowAdded,resultModel);
        return "result";
    }

}
