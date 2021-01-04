package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ResultService {

    public Model createResultModel(int rows, Model resultModel) {
        boolean isSavedSucessfully=false;
        if(rows>0){
            isSavedSucessfully = true;
        }
        resultModel.addAttribute("isSavedSucessfully",isSavedSucessfully);
        return resultModel;
    }
}
