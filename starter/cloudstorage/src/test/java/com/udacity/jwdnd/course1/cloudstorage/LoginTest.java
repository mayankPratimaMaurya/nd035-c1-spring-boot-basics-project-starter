package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class LoginTest extends CloudStorageWebDriver {

    private LoginPage loginPage;

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
    public void checkInvalidCredentialsErrorMessageForWrongCredentials(){
        reLoadURL();
        loginPage.enterCredentialsAndSubmit("invalidUsername","invaliPassword");
        Assertions.assertTrue(driver.findElement(By.id("error_message"))!=null);
        Assertions.assertEquals("Login",driver.getTitle() );
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
}


