package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    CredentialsMapper credentialsMapper;

    public CredentialService(CredentialsMapper credentialsMapper) {
        this.credentialsMapper = credentialsMapper;
    }

    public List<Credentials> getAllCredentialsForUser(int userid){
        return credentialsMapper.getAllCredentialsForUser(userid);
    }

    public Credentials getCredentialsWithCredentialId(int credentialsID){
        return credentialsMapper.getCredentialsWithCredentialId(credentialsID);
    }

    public int removeCredentials(int credentialId) {
        return credentialsMapper.removeCredentials(credentialId);
    }

    public int addNewCredentials(Credentials credentials){
        return credentialsMapper.addNewCredentials(credentials);
    }

    public int updateCredentials(Credentials credentials) {
        return credentialsMapper.updateCredentials(credentials);
    }
}
