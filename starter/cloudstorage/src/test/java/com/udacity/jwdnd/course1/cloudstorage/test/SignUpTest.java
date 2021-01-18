package com.udacity.jwdnd.course1.cloudstorage.test;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignUpPage;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.test.CloudStorageWebDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.NoSuchElementException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpTest extends CloudStorageWebDriver {

    private SignUpPage signUpPage;
    private LoginPage loginPage;
    @Autowired
    UserService service;


    @AfterEach
    void clean() {
        signUpPage = null;
    }

    private void reLoadURL() {
        this.driver = new ChromeDriver();
        driver.get("http://localhost:" + this.port + "/signup");
        signUpPage = new SignUpPage(driver);
    }

    @Test
    public void testBackToLoginPageLink() {
        reLoadURL();
        signUpPage.backToLoginClicked();
        Assertions.assertEquals("Login", driver.getTitle());
    }


    @Test
    public void checkIfSignedUpSuccessMessageIsSeenOnInitialSignUpPageLoad() {
        reLoadURL();
        Assertions.assertThrows(NoSuchElementException.class, () -> driver.findElement(By.name("signedUpSuccess")));
    }

    @Test
    public void checkIfErrorMessageIsSeenOnInitialSignUpPageLoad() {
        reLoadURL();
        Assertions.assertThrows(NoSuchElementException.class, () -> driver.findElement(By.name("signUpError")));
    }

    @Test
    void addOneUserInDBAndCheckICorrectValuesAreUpdated() {
        User user = getUser("asdfghjkl4");
        Assertions.assertEquals(1, service.createUser(user));
        Assertions.assertEquals(user.getUsername(), service.getUser(user.getUsername()).getUsername());
        Assertions.assertEquals(user.getFirstname(), service.getUser(user.getUsername()).getFirstname());
        Assertions.assertEquals(user.getLastname(), service.getUser(user.getUsername()).getLastname());
    }

    @Test
    void checkIfUsernameIsAvailableToUse() {
        User user = getUser("asdfghjkl5");
        Assertions.assertTrue(service.isUserNameAvailable(user.getUsername()));

        Assertions.assertEquals(1, service.createUser(user));

        Assertions.assertFalse(service.isUserNameAvailable(user.getUsername()));
    }

    @Test
    public void testSignUpSucessMessageFlow() {
        reLoadURL();
        User user = getUser("asdfghjkl2");
        signUpPage.fillFormAndSubmit(user);
        Assertions.assertTrue(driver.findElement(By.name("signedUpSuccess")) != null);
    }

    @Test
    public void ifUsernameIsNotAvailableToUse() throws InterruptedException {
        User user = signup("asdfghjkl3");
        Thread.sleep(2000);
        loginPage = new LoginPage(driver);
        loginPage.clickSignUpLink();
        Thread.sleep(10000);
        user.setFirstname("pratima");
        signUpPage.fillFormAndSubmit(user);
        Thread.sleep(1000);

        Assertions.assertTrue(driver.findElement(By.name("signUpError")).getText() != null);
        System.out.print(signUpPage.getErrorMessageText());
        Assertions.assertTrue(signUpPage.getErrorMessageText().equals("User Already Exists!!"));
    }

    @Test
    public void signUpAsANewUserAndLoginWithNewCredentailsFlowTest() throws InterruptedException {

        User user = signup("asdfghjkl1");
        login(user);
        Thread.sleep(2000);
        Assertions.assertEquals("Home", driver.getTitle());
    }

    private void login(User user) throws InterruptedException {

        Thread.sleep(2000);
        Assertions.assertEquals("Login", driver.getTitle());
        loginPage = new LoginPage(driver);
        loginPage.enterCredentialsAndSubmit(user.getUsername(), user.getPassword());

    }

    private User signup(String userName) throws InterruptedException {

        reLoadURL();
        User user = getUser(userName);
        signUpPage.fillFormAndSubmit(user);
        Thread.sleep(5000);
        return user;
    }

    private User getUser(String userName) {
        return new User(null, userName, null, "lkjhgfdsa", "mayank", "maurya");
    }
}
