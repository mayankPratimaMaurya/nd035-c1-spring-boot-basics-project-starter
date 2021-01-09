package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class ResultService {

    public Result result;

    public ResultService(Result result) {
        this.result = result;
    }

    public RedirectAttributes createResultModel(Result result, RedirectAttributes resultModel) {

        if (result.getErrorMessage() != null)
            resultModel.addFlashAttribute("errorMessage", result.getErrorMessage());
        else if(result.getRowsModified()>0)
            resultModel.addFlashAttribute("isSavedSucessfully",true);
        else
            resultModel.addFlashAttribute("errorMessage", "Your changes were not saved. Please try again later!!");

        return resultModel;
    }

    public void resultDataRefresh(){
        result.setRowsModified(0);
        result.setErrorMessage(null);
    }
}
