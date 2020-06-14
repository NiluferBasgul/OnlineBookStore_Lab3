package com.example.lab.service.impl;

import com.example.lab.model.Author;
import com.example.lab.model.exceptions.AuthorNotFoundException;
import com.example.lab.repository.AuthorRepository;
import com.example.lab.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public Author findById(Long authorId) {
        return this.authorRepository.findById(authorId)
                .orElseThrow(()-> new AuthorNotFoundException(authorId));
    }
}
