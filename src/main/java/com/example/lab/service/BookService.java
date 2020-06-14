package com.example.lab.service;

import com.example.lab.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface BookService {
    List<Book> findAll();
    List<Book> findAllByCategoryId(Long categoryId);
    Book findById(Long id);
    Book update(Long id, Book books, MultipartFile image) throws IOException;
    Book save(Book book, MultipartFile image) throws IOException;
    void deleteById(Long id);
}
