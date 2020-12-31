package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationProvider {

    public Authentication authenticate(Authentication var1) throws AuthenticationException{

        System.out.println("**Login authentication caleed**");
        return null;
    }
    public boolean supports(Class<?> var1){return false;}



}
