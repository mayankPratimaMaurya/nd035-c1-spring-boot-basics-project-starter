package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignUpPage;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.NoSuchElementException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpTest extends CloudStorageWebDriver{

    private SignUpPage signUpPage;
    private LoginPage loginPage;
    @Autowired
    UserService service;


    @AfterEach 
    void clean(){
        signUpPage = null;
    }

    private void reLoadURL(){
        this.driver = new ChromeDriver();
        driver.get("http://localhost:" + this.port + "/signup");
        signUpPage = new SignUpPage(driver);
    }

    @Test
    public void testBackToLoginPageLink(){
        reLoadURL();
        signUpPage.backToLoginClicked();
        Assertions.assertEquals("Login",driver.getTitle());
    }

    @Test
    public void testIfContinueToLoginPageLinkNavigatesToLoginPage(){
        reLoadURL();
        User user = new User(null, "asdfghjkl1", null, "lkjhgfdsa", "mayank","Maurya");
        signUpPage.fillFormAndSubmit(user);
        signUpPage.continueToLoginpage();
        Assertions.assertEquals("Login",driver.getTitle());
    }

    @Test
    public void checkIfSignedUpSuccessMessageIsSeenOnInitialSignUpPageLoad(){
        reLoadURL();
        Assertions.assertThrows(NoSuchElementException.class, ()-> driver.findElement(By.name("signedUpSuccess")));
    }

    @Test
    public void checkIfErrorMessageIsSeenOnInitialSignUpPageLoad(){
        reLoadURL();
        Assertions.assertThrows(NoSuchElementException.class, ()-> driver.findElement(By.name("signUpError")));
    }

    @Test
    public void testSignUpSucessMessageFlow(){
        reLoadURL();
        User user = new User(null, "asdfghjkl2", null, "lkjhgfdsa", "mayank","Maurya");
        signUpPage.fillFormAndSubmit(user);
        Assertions.assertTrue(driver.findElement(By.name("signedUpSuccess"))!=null);
    }

   @Test
   public void ifUsernameIsAvailableToUse(){

       reLoadURL();
       User user = new User(null, "asdfghjkl3", null, "lkjhgfdsa", "mayank","Maurya");
       signUpPage.fillFormAndSubmit(user);
       signUpPage.fillFormAndSubmit(user);
        Assertions.assertTrue(driver.findElement(By.name("signUpError"))!=null);
   }

    @Test
    void addOneUserInDBAndCheckICorrectValuesAreUpdated(){
        User user = new User(null, "asdfghjkl4", null, "lkjhgfdsa", "mayank", "maurya");
        Assertions.assertEquals(1,service.createUser(user));
        Assertions.assertEquals(user.getUsername(),service.getUser(user.getUsername()).getUsername());
        Assertions.assertEquals(user.getFirstname(),service.getUser(user.getUsername()).getFirstname());
        Assertions.assertEquals(user.getLastname(),service.getUser(user.getUsername()).getLastname());
    }

    @Test
    void checkIfUsernameIsAvailableToUse(){
        User user = new User(null, "asdfghjkl5", null, "lkjhgfdsa", "mayank", "maurya");
        Assertions.assertTrue(service.isUserNameAvailable(user.getUsername()));

        Assertions.assertEquals(1,service.createUser(user));

        Assertions.assertFalse(service.isUserNameAvailable(user.getUsername()));
    }
}
