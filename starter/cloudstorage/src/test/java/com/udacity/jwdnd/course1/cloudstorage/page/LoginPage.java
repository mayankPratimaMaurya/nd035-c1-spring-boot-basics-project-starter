package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(name = "username")
    WebElement username;

    @FindBy(name="password")
    WebElement password;

    @FindBy(name = "submitLogin")
    WebElement submitLogin;

    @FindBy(name="signUpLinkClick")
    WebElement signUpLinkClick;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void enterCredentialsAndSubmit(String username, String password){

        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.submitLogin.click();
    }

    public void clickSignUpLink(){

        this.signUpLinkClick.click();
    }
}
