package com.udacity.jwdnd.course1.cloudstorage.test;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignUpPage;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloudStorageWebDriver {

	@LocalServerPort
	protected int port;

	protected WebDriver driver;

	protected LoginPage loginPage;
	protected SignUpPage signUpPage;
	protected HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@AfterEach
	protected void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
		loginPage=null;
		homePage=null;
		signUpPage=null;
	}

	protected void loadLoginPage(){
		driver.get("http://localhost:" + this.port + "/login");
		initializePages();
		Assertions.assertEquals("Login",driver.getTitle());
	}

	protected void initializePages() {
		loginPage = new LoginPage(driver);
		signUpPage = new SignUpPage(driver);
		homePage = new HomePage(driver);
	}

	protected void loginWithSignedUpUserNamePassword(String username, String password) throws InterruptedException{
		loginPage.enterCredentialsAndSubmit(username,password);
		Thread.sleep(3000);
		Assertions.assertEquals("Home",driver.getTitle());
	}


}
