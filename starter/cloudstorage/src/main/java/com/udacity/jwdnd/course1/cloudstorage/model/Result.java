package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.stereotype.Component;

@Component
public class Result {

    private int rowsModified;
    private String errorMessage;
    private String successMessage;

    public int getRowsModified() {
        return rowsModified;
    }

    public void setRowsModified(int rowsModified) {
        this.rowsModified = rowsModified;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
