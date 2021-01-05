package com.udacity.jwdnd.course1.cloudstorage.test;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTest {

    @Autowired
    CredentialService credentialsService;

    List<Credentials> credentialsList;
    static int currentCredentialID;

    @BeforeEach
    void addNewCredentials() {
        credentialsList = new ArrayList<>();
        credentialsList.add(new Credentials(0, "https://www.youtube.com", "shamindrashamindra", "", "Qdfkjghdfg#23445350", 1));
        credentialsList.add(new Credentials(0, "https://www.facebook.com", "tushiara", "", "Fjhdlfgjhd@8972348", 1));
        credentialsList.add(new Credentials(0, "https://www.gmail.com", "kiaraa", "", "sfsdlkfh#45E#234", 1));

        int credentialCount=0;
        for(Credentials credentials:credentialsList){
            Assertions.assertEquals(1,credentialsService.addNewCredentials(credentials));
            currentCredentialID++;
            Assertions.assertEquals(++credentialCount, credentialsService.getAllCredentialsForUser(1).stream().count());
        }
    }

    @AfterEach
    void cleanUpOrRemoveAllCredentials(){

        Long credentialCount = credentialsService.getAllCredentialsForUser(1).stream().count();
        for(Credentials credentials:credentialsService.getAllCredentialsForUser(1)){
            Assertions.assertEquals(1,credentialsService.removeCredentials(credentials.getCredentialid()));
            Assertions.assertEquals(--credentialCount, credentialsService.getAllCredentialsForUser(1).stream().count());
        }

    }

    @Test
    public void fetchCredentialsListCountTest(){
        Assertions.assertEquals(3, credentialsService.getAllCredentialsForUser(1).stream().count());
    }


    @Test
    public void addNewCredentialsTest(){
        Credentials newCredentials = new Credentials(0,"https//:TEST.COM","test1","","passwordtest",1);
        long count = credentialsService.getAllCredentialsForUser(1).stream().count();
        Assertions.assertEquals(1,credentialsService.addNewCredentials(newCredentials));
        currentCredentialID++;
        Assertions.assertEquals(count+1,credentialsService.getAllCredentialsForUser(1).stream().count());
    }

    @Test
    public void getCredentialsWithCredentialsIDTest(){
        Credentials  credentials = credentialsService.getCredentialsWithCredentialId(currentCredentialID);
        Assertions.assertEquals("https://www.gmail.com", credentials.getUrl());
        Assertions.assertNotEquals("sfsdlkfh#45E#234", credentials.getPassword());
        Assertions.assertEquals("sfsdlkfh#45E#234",credentials.getUnEncryptedPassword());
        Assertions.assertEquals("kiaraa",credentials.getUsername());

    }

    @Test
    public void updateCredentialsTestAlongWithEncryptionAndDecryptionPassword(){
        Credentials credentials = credentialsService.getCredentialsWithCredentialId(currentCredentialID);
        credentials.setUrl("https://changedURLcom");
        credentials.setUsername("updatedUsername");
        credentials.setPassword("updatedPassword");

        Assertions.assertEquals(1,credentialsService.updateCredentials(credentials));
        credentials = credentialsService.getCredentialsWithCredentialId(currentCredentialID);
        Assertions.assertEquals("https://changedURLcom",credentials.getUrl());
        Assertions.assertEquals("updatedUsername",credentials.getUsername());
        Assertions.assertNotEquals("updatedPassword",credentials.getPassword());
        Assertions.assertEquals("updatedPassword",credentials.getUnEncryptedPassword());

    }

    @Test
    public void newCredentialsTestAlongWithEncryptionAndDecryptionPassword(){
        Credentials newCredentials = new Credentials(0,"https://tes.google.com","testUsernameUsername","","222^UUpasswordtest",1);
        Assertions.assertEquals(1,credentialsService.addNewCredentials(newCredentials));
        currentCredentialID++;
        for (Credentials cder: credentialsService.getAllCredentialsForUser(1)){
            if(cder.getUsername().equals("testUsernameUsername")){
                Assertions.assertNotEquals("222^UUpasswordtest", cder.getPassword());
                Assertions.assertEquals("222^UUpasswordtest",cder.getUnEncryptedPassword());
                Assertions.assertEquals("testUsernameUsername",cder.getUsername());
            }
        }
    }

   }
