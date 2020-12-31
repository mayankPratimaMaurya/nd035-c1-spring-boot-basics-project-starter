package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

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
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() throws InterruptedException{
		driver.get("http://localhost:" + this.port + "/login");
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
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertThrows(NoSuchElementException.class,()-> driver.findElement(By.id("error_message")));
	}

	@Test
	public void checkIfLoggedOutMessageIsShownWithoutLogin() throws InterruptedException{
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertThrows(NoSuchElementException.class,()-> driver.findElement(By.id("logged_out_message")));
	}
}
