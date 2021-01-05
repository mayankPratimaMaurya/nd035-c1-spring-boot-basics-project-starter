package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credentials> getAllCredentialsForUser(int userid){

        List<Credentials> credentialsList = credentialsMapper.getAllCredentialsForUser(userid);
        for(Credentials credentials:credentialsList){
            credentials.setUnEncryptedPassword(getDecryptedPassword(credentials));
        }
        return credentialsList;
    }


    public Credentials getCredentialsWithCredentialId(int credentialsID){

        Credentials credentials = credentialsMapper.getCredentialsWithCredentialId(credentialsID);
        credentials.setUnEncryptedPassword(getDecryptedPassword(credentials));
        return credentials;
    }

    public int removeCredentials(int credentialId) {
        return credentialsMapper.removeCredentials(credentialId);
    }

    public int addNewCredentials(Credentials credentials){

        credentials = encryptPasswordAndAddKeyToCredentials(credentials);
        return credentialsMapper.addNewCredentials(credentials);
    }

    public int updateCredentials(Credentials credentials) {

        credentials = encryptPasswordAndAddKeyToCredentials(credentials);
        return credentialsMapper.updateCredentials(credentials);
    }

    private Credentials encryptPasswordAndAddKeyToCredentials(Credentials credentials){

        String encodedKey = createEncodedKey();
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);
        credentials.setPassword(encryptedPassword);
        credentials.setKey(encodedKey);
        return credentials;
    }

    private String createEncodedKey() {

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }

    private String getDecryptedPassword(Credentials credentials) {

        String encryptedPassword = credentials.getPassword();
        String encodedKey = credentials.getKey();
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        return decryptedPassword;
    }
}
