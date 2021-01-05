package com.udacity.jwdnd.course1.cloudstorage.test;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTest {

    @Autowired
    CredentialService credentialsService;

    @Test
    public void addNewCredentialsForUser(){

    }
    @Test
    public void getCredentialsListTest(){
        Assertions.assertEquals(4, credentialsService.getAllCredentialsForUser(1).stream().count());
    }

    @Test
    public void deleteCredentialsTest(){
        long count = credentialsService.getAllCredentialsForUser(1).stream().count();
        Assertions.assertEquals(1,credentialsService.removeCredentials(1) );
        Assertions.assertEquals(count-1, credentialsService.getAllCredentialsForUser(1).stream().count());
    }

    @Test
    public void addNewCredentialsTest(){
        Credentials newCredentials = new Credentials(0,"https:google","test1","testkey","passwordtest",1);
        long count = credentialsService.getAllCredentialsForUser(1).stream().count();
        Assertions.assertEquals(1,credentialsService.addNewCredentials(newCredentials));
        Assertions.assertEquals(count+1,credentialsService.getAllCredentialsForUser(1).stream().count());
    }

    @Test
    public void getCredentialsWithCredentialsIDTest(){
        Credentials  credentials = credentialsService.getCredentialsWithCredentialId(1);
        Assertions.assertEquals("https://google.com", credentials.getUrl());
    }

    @Test
    public void updateNewCredentials(){
        Credentials credentials = credentialsService.getCredentialsWithCredentialId(1);
        credentials.setUrl("updatedHttps:Goole");
        credentials.setUsername("updatedUsername");
        credentials.setPassword("updatedPassword");

        Assertions.assertEquals(1,credentialsService.updateCredentials(credentials));
        credentials = credentialsService.getCredentialsWithCredentialId(1);
        Assertions.assertEquals("updatedHttps:Goole",credentials.getUrl());
        Assertions.assertEquals("updatedUsername",credentials.getUsername());
        Assertions.assertEquals("updatedPassword",credentials.getPassword());
    }

}
