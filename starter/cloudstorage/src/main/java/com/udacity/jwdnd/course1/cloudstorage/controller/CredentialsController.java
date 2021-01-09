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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    String deleteCredentials(@ModelAttribute("credentialsToDelete") Credentials credentialsToDelete, RedirectAttributes resultModel){
        resultService.resultDataRefresh();
        int credentialsDeleted = credentialsService.removeCredentials(credentialsToDelete.getCredentialid());
        resultService.result.setRowsModified(credentialsDeleted);
        resultModel = resultService.createResultModel(resultService.result,resultModel);
        return "redirect:/home";
    }

    @PostMapping("addEditCredentials")
    String addOrEditCredentials(@ModelAttribute("addOrEditCredentials") Credentials addOrEditCredentials,
                                Authentication authentication,
                                RedirectAttributes resultModel){
        resultService.resultDataRefresh();
        addOrEditCredentials.setUserid(userService.getUserID(authentication));
        int rowAdded=0;
        if(addOrEditCredentials.getCredentialid() == null)
            rowAdded = credentialsService.addNewCredentials(addOrEditCredentials);
        else
            rowAdded = credentialsService.updateCredentials(addOrEditCredentials);

        resultService.result.setRowsModified(rowAdded);
        resultModel = resultService.createResultModel(resultService.result,resultModel);
        return "redirect:/home";
    }

}
