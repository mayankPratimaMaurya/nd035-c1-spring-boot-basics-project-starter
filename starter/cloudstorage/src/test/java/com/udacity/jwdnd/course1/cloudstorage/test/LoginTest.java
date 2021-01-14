package com.udacity.jwdnd.course1.cloudstorage.test;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignUpPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class LoginTest extends CloudStorageWebDriver {

    private LoginPage loginPage;
    private SignUpPage signUpPage;

    @AfterEach
    public void cleanPage() {
        System.out.println("clean");
        loginPage=null;
    }

    @Test
    public void getLoginPage() throws InterruptedException{
        reLoadURL();
        Thread.sleep(500);
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void checkIfHomePageLoadsWithoutLogin() throws InterruptedException{
        this.driver = new ChromeDriver();
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(500);
        Assertions.assertNotEquals("Home",driver.getTitle());
        Assertions.assertEquals("Login",driver.getTitle());
    }

    @Test
    public void checkIfSignUpPageLoadsWithoutLogin() throws InterruptedException{
        this.driver = new ChromeDriver();
        driver.get("http://localhost:" + this.port + "/signup");
        Thread.sleep(500);
        Assertions.assertEquals("Sign Up",driver.getTitle());
        Assertions.assertNotEquals("Login",driver.getTitle());
    }

    @Test
    public void checkIfErrorMessageMessageIsShownOnInitialLoginPageLoad() throws InterruptedException{
        reLoadURL();
        Assertions.assertThrows(NoSuchElementException.class,()-> driver.findElement(By.id("error_message")));
    }

    @Test
    public void checkIfLoggedOutMessageIsShownOnInitialLoginPageLoad() throws InterruptedException{
        reLoadURL();
        Assertions.assertThrows(NoSuchElementException.class,()-> driver.findElement(By.id("logged_out_message")));
    }

    @Test
    public void checkInvalidCredentialsErrorMessageForWrongPassword() throws InterruptedException{
        User user = signUp("asdfghjkl123");
        loginWithWrongCredentails(user.getUsername(),"invalidPassword");
        Thread.sleep(2000);
        assertWrongCredentials();
    }


    @Test
    public void checkInvalidCredentialsErrorMessageForWrongUsername() throws InterruptedException{
        User user = signUp("qwertyuio123");
        loginWithWrongCredentails("invalidUsername",user.getPassword());
        Thread.sleep(2000);
        assertWrongCredentials();
    }

    @Test
    public void checkSignUpPageOnShowSignUpLinkClick() throws InterruptedException {
        reLoadURL();
        loginPage.clickSignUpLink();
        Thread.sleep(5000);
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    private void reLoadURL(){
        this.driver = new ChromeDriver();
        driver.get("http://localhost:" + this.port + "/login");
        loginPage = new LoginPage(driver);
    }

    private User signUp(String username) throws InterruptedException{

        User user = new User(null, username, null, "lkjhgfdsa", "mayank","Maurya");
        loadSignupPage();
        signUpPage.fillFormAndSubmit(user);
        return user;
    }

    private void loadSignupPage() throws InterruptedException {

        reLoadURL();
        loginPage.clickSignUpLink();
        Thread.sleep(5000);
        Assertions.assertEquals("Sign Up", driver.getTitle());
        signUpPage = new SignUpPage(driver);
    }

    private void loginWithWrongCredentails(String username , String password) throws InterruptedException{
        loadLoginPage();
        loginPage.enterCredentialsAndSubmit(username,password);
    }

    private void loadLoginPage() throws InterruptedException {

        driver.get("http://localhost:" + this.port + "/login");
        loginPage = new LoginPage(driver);
        Assertions.assertEquals("Login",driver.getTitle());
    }



    private void assertWrongCredentials() {
        Assertions.assertTrue(driver.findElement(By.name("error_message")).getText().contains("Invalid username or password"));
        Assertions.assertEquals("Login", driver.getTitle());
    }
}


