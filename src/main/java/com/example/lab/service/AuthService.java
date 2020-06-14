package com.example.lab.service;

import com.example.lab.model.User;

public interface AuthService {
    User getCurrentUser();
    String getCurrentUserId();
}
