package com.udacity.jwdnd.course1.cloudstorage.test;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.test.CloudStorageWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesFlowRubricTest extends CloudStorageWebDriver {

    @Autowired
    UserService userService;

    static boolean isUserCreated;

    @BeforeEach
    void loginWithExistingUser() throws InterruptedException{
        this.driver = new ChromeDriver();
        if(!isUserCreated){
            userService.createUser(user("mayankNotesUser"));
            isUserCreated=true;
        }
        loadLoginPage();
        loginWithSignedUpUserNamePassword("mayankNotesUser","lkjhgfdsa");
    }

    @Test
    public void createAndVerifyNotesTest() throws InterruptedException{
        homePage.selectNotesTabAndSelectAddNewButton();
        Thread.sleep(1000);
        homePage.addNewNoteAndSubmit("title123","title123Description");
        Thread.sleep(1000);
        Assertions.assertTrue(homePage.getNoteTabTableContent().stream().anyMatch(notes -> notes.getNotetitle().equals("title123")));
        Assertions.assertTrue(homePage.getNoteTabTableContent().stream().anyMatch(notes -> notes.getNotedescription().equals("title123Description")));
    }

    @Test
    public void createAndDeleteNotesAndValidateTest() throws InterruptedException{
        homePage.selectNotesTabAndSelectAddNewButton();
        Thread.sleep(1000);
        homePage.addNewNoteAndSubmit("title123456","title123Description456");
        Thread.sleep(1000);
        Assertions.assertTrue(homePage.getNoteTabTableContent().stream().anyMatch(notes -> notes.getNotetitle().equals("title123456")));
        homePage.deleteNoteWith("title123456","title123Description456");
        Thread.sleep(1000);
        Assertions.assertFalse(homePage.getNoteTabTableContent().stream().anyMatch(notes -> notes.getNotetitle().equals("title123456")));
    }

    @Test
    public void createAndEditNotesAndValidateTest() throws InterruptedException{

        homePage.selectNotesTabAndSelectAddNewButton();
        Thread.sleep(1000);
        homePage.addNewNoteAndSubmit("title","titleDescription");
        Thread.sleep(1000);
        Assertions.assertTrue(homePage.getNoteTabTableContent().stream().anyMatch(notes -> notes.getNotetitle().equals("title")));
        homePage.openModelToEditNotesFor("title","titleDescription");
        Thread.sleep(1000);
        homePage.addNewNoteAndSubmit("edit","edit");
        Thread.sleep(1000);
        Assertions.assertTrue(homePage.getNoteTabTableContent().stream().anyMatch(notes -> notes.getNotetitle().equals("titleedit")));
        Assertions.assertTrue(homePage.getNoteTabTableContent().stream().anyMatch(notes -> notes.getNotedescription().equals("titleDescriptionedit")));
    }


    private User user(String userName) {
        return new User(null, userName, null, "lkjhgfdsa", "mayankNotesUser", "maurya");
    }

}
