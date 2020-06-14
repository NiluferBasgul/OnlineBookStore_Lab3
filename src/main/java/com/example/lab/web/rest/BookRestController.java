package com.example.lab.web.rest;

import com.example.lab.model.Book;
import com.example.lab.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/books")
public class BookRestController {
    private BookService bookService;

    public BookRestController() { }

    public BookRestController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> findAll(){ return this.bookService.findAll();}

    @GetMapping("/by-category/{categoryId}")
    public List<Book> findAllByCategoryId(@PathVariable Long categoryId){ return this.bookService.findAllByCategoryId(categoryId);}

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid Book book, @RequestParam MultipartFile image) throws IOException {return this.bookService.update(id, book, image);}

    @PostMapping
    public Book save(@Valid Book book, @RequestParam MultipartFile image) throws IOException { return this.bookService.save(book, image);}

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){this.bookService.deleteById(id);}
}
