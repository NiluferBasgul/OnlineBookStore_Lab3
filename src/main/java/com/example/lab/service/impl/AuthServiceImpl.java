package com.example.lab.service.impl;

import com.example.lab.model.User;
import com.example.lab.repository.UserRepository;
import com.example.lab.service.AuthService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User getCurrentUser() {
//        return this.userRepository.findById("dummy").orElseGet(()->{
//            User user = new User();
//            user.setUsername("dummy");
//            return this.userRepository.save(user);
//        });
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @Override
    public String getCurrentUserId() {
        return this.getCurrentUser().getUsername();
    }
}
