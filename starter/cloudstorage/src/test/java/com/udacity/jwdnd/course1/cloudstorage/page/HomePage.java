package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    @FindBy(name="logoutUser")
    WebElement logoutUser;

    @FindBy(name="addNewNote")
    WebElement addNewNote;

    @FindBy(name="addNewCredentials")
    WebElement addNewCredentials;


    @FindBy(id="note-title")
    WebElement noteTitle;

    @FindBy(id="note-description")
    WebElement noteDescription;

    @FindBy(id="credential-url")
    public WebElement url;

    @FindBy(id="credential-username")
    public WebElement username;

    @FindBy(id="credential-password")
    public WebElement password;

    @FindBy(id="noteSubmitBtn")
    WebElement noteSubmit;

    @FindBy(id="credentialSubmitBtn")
    WebElement credentialSubmit;

    @FindBy(id="nav-notes-tab")
    WebElement notesTab;

    @FindBy(id="nav-credentials-tab")
    WebElement credentialTab;

    @FindBy(id="userTable")
    public WebElement userTable;

    @FindBy(id="credentialTable")
    public WebElement credentialTable;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void clickLogoutButton(){

        logoutUser.click();
    }

    public void selectNotesTabAndSelectAddNewButton() throws InterruptedException{
        notesTab.click();
        Thread.sleep(500);
        addNewNote.click();
    }

    public void selectCredentialsTabAndSelectAddNewButton() throws InterruptedException{
        credentialTab.click();
        Thread.sleep(500);
        addNewCredentials.click();
    }

    public List<Notes> getNoteTabTableContent(){

        List<WebElement> rows= userTable.findElements(By.id("userRowData"));
        List<Notes> notesList = new ArrayList<Notes>();

        for(WebElement row:rows){
            Notes note = new Notes(0,
                    row.findElement(By.id("rowNoteTitle")).getText(),
                    row.findElement(By.id("rowNoteDescription")).getText(),
                    0);
            notesList.add(note);
        }

        return notesList;
    }

    public List<Credentials> getCredentialsTabTableContent(){

        List<WebElement> rows= credentialTable.findElements(By.id("credetailsRow"));
        List<Credentials> credentialsList = new ArrayList<Credentials>();

        for(WebElement row:rows){
            Credentials credentials = new Credentials(0,row.findElement(By.id("rowUrlTitle")).getText(),row.findElement(By.id("rowUrlUsername")).getText(),null,null,0);
            credentialsList.add(credentials);
        }

        return credentialsList;
    }

    public void deleteNoteWith(String url, String username){

        List<WebElement> rows= userTable.findElements(By.id("userRowData"));
        for(WebElement row:rows){
            if (row.findElement(By.id("rowNoteTitle")).getText().equals(url) && row.findElement(By.id("rowNoteDescription")).getText().equals(username)) {
                row.findElement(By.id("deleteNoteBtn")).click();
            }
        }
    }

    public void deleteCredentialsWith(String url, String username){

        List<WebElement> rows= credentialTable.findElements(By.id("credetailsRow"));
        for(WebElement row:rows){
            if (row.findElement(By.id("rowUrlTitle")).getText().equals(url) && row.findElement(By.id("rowUrlUsername")).getText().equals(username)) {
                row.findElement(By.id("deleteCredBtn")).click();
            }
        }
    }

    public void openModelToEditNotesFor(String title, String description){

        List<WebElement> rows= userTable.findElements(By.id("userRowData"));
        for(WebElement row:rows){
            if (row.findElement(By.id("rowNoteTitle")).getText().equals(title) && row.findElement(By.id("rowNoteDescription")).getText().equals(description)) {
                row.findElement(By.id("editNoteBtn")).click();
            }
        }
    }

    public void openModelToEditCredentialsFor(String url, String username){

        List<WebElement> rows= credentialTable.findElements(By.id("credetailsRow"));
        System.out.println(rows);

        for(WebElement row:rows){
            if (row.findElement(By.id("rowUrlTitle")).getText().equals(url) && row.findElement(By.id("rowUrlUsername")).getText().equals(username)) {
                row.findElement(By.id("editCredentialsBtn")).click();
            }
        }
    }

    public void addNewNoteAndSubmit(String title, String description) throws InterruptedException{
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        Thread.sleep(500);
        noteSubmit.click();
    }

    public void addNewCredentialsAndSubmit(String url, String username, String password) throws InterruptedException{
        this.url.sendKeys(url);
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        Thread.sleep(500);
        credentialSubmit.click();
        Thread.sleep(1000);
    }

}
