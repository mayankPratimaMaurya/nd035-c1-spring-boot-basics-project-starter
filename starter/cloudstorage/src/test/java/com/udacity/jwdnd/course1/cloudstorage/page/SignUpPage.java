package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(name="backToLogin")
    WebElement backToLogin;

    @FindBy(name="firstname")
    WebElement firstname;

    @FindBy(name="lastname")
    WebElement lastname;

    @FindBy(name="username")
    WebElement username;

    @FindBy(name="password")
    WebElement password;

    @FindBy(name="submit_SignUp")
    WebElement submit_SignUp;

    @FindBy(name="signUpError")
    WebElement signUpError;


    public SignUpPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void fillFormAndSubmit(User user){

        this.firstname.sendKeys(user.getFirstname());
        this.lastname.sendKeys(user.getLastname());
        this.username.sendKeys(user.getUsername());
        this.password.sendKeys(user.getPassword());

        this.submit_SignUp.click();
    }

    public void backToLoginClicked(){
        this.backToLogin.click();
    }

    public String getErrorMessageText(){
        return this.signUpError.getText();
    }

}
