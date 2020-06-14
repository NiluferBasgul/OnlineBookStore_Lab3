package com.example.lab.repository.impl;

import com.example.lab.model.Book;
import com.example.lab.repository.BookRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl implements BookRepository{

    private HashMap<Long, Book> books;
    private Long counter;

    @PostConstruct
    public void init(){
        this.books = new HashMap<>();
        this.counter = 0L;
    }

    private Long generateKey() {
        return counter++;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(this.books.values());
    }

    @Override
    public List<Book> findAllByCategoryId(Long categoryId) {
        return this.books.values()
                .stream()
                .filter(item -> item.getCategory().equals(categoryId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(this.books.get(id));
    }

    @Override
    public Book save(Book book) {
        if(book.getId() == null){
            book.setId(this.generateKey());
        }else {
            this.books.put(book.getId(), book);
        }
        return book;
    }

    @Override
    public void deleteById(Long id) {
        this.books.remove(id);
    }
}
