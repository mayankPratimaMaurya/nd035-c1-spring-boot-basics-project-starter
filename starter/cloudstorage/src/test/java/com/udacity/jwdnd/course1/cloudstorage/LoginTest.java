package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class LoginTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private LoginPage loginPage;
    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        loginPage=null;
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() throws InterruptedException{
        reLoadLoginURL();
        Thread.sleep(500);

        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void checkIfHomePageLoadsWithoutLogin() throws InterruptedException{
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(500);
        Assertions.assertNotEquals("Home",driver.getTitle());
        Assertions.assertEquals("Login",driver.getTitle());

    }

    @Test
    public void checkIfErrorMessageMessageIsShownWithoutLogin() throws InterruptedException{
        reLoadLoginURL();
        Assertions.assertThrows(NoSuchElementException.class,()-> driver.findElement(By.id("error_message")));
    }

    @Test
    public void checkIfLoggedOutMessageIsShownWithoutLogin() throws InterruptedException{
        reLoadLoginURL();
        Assertions.assertThrows(NoSuchElementException.class,()-> driver.findElement(By.id("logged_out_message")));
    }

    @Test
    public void showInvalidCredentialsErrorMessageForWrongCredentials(){
        reLoadLoginURL();
        loginPage.enterCredentialsAndSubmit("invalidUsername","invaliPassword");
        Assertions.assertTrue(driver.findElement(By.id("error_message"))!=null);
        Assertions.assertEquals("Login",driver.getTitle() );
    }

    @Test
    public void showSignUpPageOnShowSignUpLinkClick() throws InterruptedException {
        reLoadLoginURL();
        loginPage.clickSignUpLink();
        Thread.sleep(5000);
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    private void reLoadLoginURL(){
        driver.get("http://localhost:" + this.port + "/login");
        loginPage = new LoginPage(driver);
    }
}


