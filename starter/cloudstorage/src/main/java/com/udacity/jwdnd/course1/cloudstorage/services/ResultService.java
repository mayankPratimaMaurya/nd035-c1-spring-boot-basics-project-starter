package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ResultService {

    public Result result;

    public ResultService(Result result) {
        this.result = result;
    }

    public Model createResultModel(Result result, Model resultModel) {

        boolean isSavedSucessfully=false;
        if(result.getRowsModified()>0)
            isSavedSucessfully = true;

        if (result.getErrorMessage() != null){
            resultModel.addAttribute("errorMessage", result.getErrorMessage());
        }
        else
            resultModel.addAttribute("isSavedSucessfully",isSavedSucessfully);

        return resultModel;
    }
}
