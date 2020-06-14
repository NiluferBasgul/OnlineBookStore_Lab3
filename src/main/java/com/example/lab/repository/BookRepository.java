package com.example.lab.repository;

import com.example.lab.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository {
    List<Book> findAll();
    List<Book> findAllByCategoryId(Long categoryId);
    Optional<Book> findById(Long id);
    Book save(Book books);
    void deleteById(Long id);
}
