package com.example.lab.service;

import com.example.lab.model.Author;

public interface AuthorService {
    Author findById(Long authorId);
}
