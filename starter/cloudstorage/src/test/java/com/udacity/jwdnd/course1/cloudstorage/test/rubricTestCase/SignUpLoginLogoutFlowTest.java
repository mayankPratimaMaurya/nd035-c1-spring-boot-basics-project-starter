package com.udacity.jwdnd.course1.cloudstorage.test.rubricTestCase;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignUpPage;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.test.CloudStorageWebDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpLoginLogoutFlowTest extends CloudStorageWebDriver {


    @Autowired
    UserService userService;

    @Autowired
    HashService hashService;

    @AfterEach
    public void cleanPage() {
        System.out.println("clean");
        loginPage=null;
    }

    @BeforeEach
    public void initDriver(){
        this.driver = new ChromeDriver();
    }

    @Test
    public void checkIfHomePageLoadsWithoutLogin() throws InterruptedException{
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(500);
        Assertions.assertNotEquals("Home",driver.getTitle());
        Assertions.assertEquals("Login",driver.getTitle());
    }

    @Test
    public void signUpToLoginToLogoutFlow() throws InterruptedException{
        loadLoginPage();
        clickSignUpLink();
        User signedUpUser =  signUpWithUserName("zxzcvbnmpoiuyt");
        loginWithSignedUpUserNamePassword(signedUpUser.getUsername(),signedUpUser.getPassword());
        logoutLoggedInUser();
    }


//    private void loadLoginPage(){
//        driver.get("http://localhost:" + this.port + "/login");
//        initializePages();
//        Assertions.assertEquals("Login",driver.getTitle());
//    }
//
//    private void initializePages() {
//        loginPage = new LoginPage(driver);
//        signUpPage = new SignUpPage(driver);
//        homePage = new HomePage(driver);
//    }

    private void clickSignUpLink() throws InterruptedException {
        loginPage.clickSignUpLink();
        Thread.sleep(5000);
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    private User signUpWithUserName(String userName) throws InterruptedException {
        User user = user(userName);
        signUpPage.fillFormAndSubmit(user);
        Thread.sleep(5000);
        assertAfterSignUp(user);
        return getUserFromDB(user.getUsername());
    }


    private void assertAfterSignUp(User user) {
        Assertions.assertEquals("zxzcvbnmpoiuyt",userService.getUser(user.getUsername()).getUsername());
        Assertions.assertEquals("Login",driver.getTitle());
        Assertions.assertTrue(driver.findElement(By.name("signedUpSuccess")) != null);
        Assertions.assertTrue(driver.findElement(By.name("signedUpSuccess")).getText().contains("You successfully signed up! Please continue to login."));
    }

    private User user(String userName) {
        return new User(null, userName, null, "lkjhgfdsa", "mayank", "maurya");
    }

    private User getUserFromDB(String userName)
    {
        User userFromDB = userService.getUser(userName);
        userFromDB.setPassword("lkjhgfdsa");
        return userFromDB;
    }

//    private void loginWithSignedUpUserNamePassword(String username, String password) throws InterruptedException{
//        loginPage.enterCredentialsAndSubmit(username,password);
//        Thread.sleep(3000);
//        Assertions.assertEquals("Home",driver.getTitle());
//    }

    private void logoutLoggedInUser() throws InterruptedException{

        homePage.clickLogoutButton();
        Thread.sleep(5000);
        assertAfterLogout();
    }

    private void assertAfterLogout() {

        Assertions.assertEquals("Login",driver.getTitle());
        Assertions.assertTrue(driver.findElement(By.name("logoutSuccess")) != null);
        Assertions.assertTrue(driver.findElement(By.name("logoutSuccess")).getText().contains("You have been logged out"));
    }
}
