package com.example.project.users.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class MySecurityService {

    // here you can write your logic on what basis user can get access of this method

    public boolean isUser(Authentication authentication, String resourceId) {
       return authentication.getName().equals(resourceId);
    }
}