package com.udacity.jwdnd.course1.cloudstorage.test.rubricTestCase;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.test.CloudStorageWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class CredentialsFlowRubricTest extends CloudStorageWebDriver {

    @Autowired
    UserService userService;

    static boolean isUserCreated;

    @BeforeEach
    void loginWithExistingUser() throws InterruptedException{
        this.driver = new ChromeDriver();
        if(!isUserCreated){
            userService.createUser(user("mayankCreUser"));
            isUserCreated=true;
        }
        loadLoginPage();
        loginWithSignedUpUserNamePassword("mayankCreUser","lkjhgfdsa");
    }



    @Test
    public void createAndVerifyCredentialsTest() throws InterruptedException{

        createCredentials("https://facebook.com","facebookUser","facebookPassword");
        verifyCreatedOrEditedCredentials("https://facebook.com","facebookUser");
    }

    @Test
    public void createAndDeleteCredentailsAndValidateTest() throws InterruptedException{
        createCredentials("https://instacart.com","instacartUser","instacartPassword");
        verifyCreatedOrEditedCredentials("https://instacart.com","instacartUser");
        homePage.deleteCredentialsWith("https://instacart.com","instacartUser");
        verifyDeletedCredentials("https://instacart.com","instacartUser");

    }

    @Test
    public void createAndEditCredentailsAndValidateTest() throws InterruptedException{

        createCredentials("https://gmail.com","gmailUser","gmailPassword");
        verifyCreatedOrEditedCredentials("https://gmail.com","gmailUser");

        openAndEditCredentialsModalFor("https://gmail.com","gmailUser","gmailPassword");
        verifyCreatedOrEditedCredentials("https://gmail.com.co","gmailUseredit");
    }

    private void createCredentials(String url, String username, String password) throws InterruptedException{
        homePage.selectCredentialsTabAndSelectAddNewButton();
        Thread.sleep(1000);
        homePage.addNewCredentialsAndSubmit(url,username,password);
        Thread.sleep(1000);
    }

    private void verifyCreatedOrEditedCredentials(String url, String username){

        Assertions.assertTrue(homePage.getCredentialsTabTableContent().stream().anyMatch(credentials -> credentials.getUrl().equals(url)));
        Assertions.assertTrue(homePage.getCredentialsTabTableContent().stream().anyMatch(credentials -> credentials.getUsername().equals(username)));
    }

    private void openAndEditCredentialsModalFor(String url, String username, String password) throws InterruptedException{
        homePage.openModelToEditCredentialsFor(url,username);
        Thread.sleep(1000);
        homePage.addNewCredentialsAndSubmit(".co","edit","edit");

    }

    private void verifyDeletedCredentials(String url, String username) throws InterruptedException{
        Thread.sleep(1000);
        Assertions.assertFalse(homePage.getCredentialsTabTableContent().stream().anyMatch(credentials -> credentials.getUrl().equals(url)));
        Assertions.assertFalse(homePage.getCredentialsTabTableContent().stream().anyMatch(credentials -> credentials.getUsername().equals(username)));
    }


        private User user(String userName) {
        return new User(null, userName, null, "lkjhgfdsa", "mayankNotesUser", "maurya");
    }
}
