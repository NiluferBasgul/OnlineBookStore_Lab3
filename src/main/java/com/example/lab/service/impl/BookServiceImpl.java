package com.example.lab.service.impl;

import com.example.lab.model.Book;
import com.example.lab.model.Category;
import com.example.lab.model.exceptions.BookNotFoundException;
import com.example.lab.repository.BookRepository;
import com.example.lab.service.BookService;
import com.example.lab.service.CategoryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;



    public BookServiceImpl(BookRepository bookRepository, @Lazy CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public List<Book> findAllByCategoryId(Long categoryId) {
        return this.bookRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Book findById(Long id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Book update(Long id, Book books, MultipartFile image) throws IOException {
        Book book = this.findById(id);
        Category category = this.categoryService.findById(books.getCategory().getId());
        book.setCategory(book.getCategory());
        book.setId(book.getId());
        book.setQuantity(book.getQuantity());
        book.setName(book.getName());
        if(image!=null && !image.getName().isEmpty()){
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            book.setImageBase64(base64Image);
        }
        return this.bookRepository.save(book);
    }

    @Override
    public Book save(Book book, MultipartFile image) throws IOException{
        Category category = this.categoryService.findById(book.getCategory().getId());
        book.setCategory(category);
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            book.setImageBase64(base64Image);
        }
        return this.bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }
}
