package com.example.lab.web;


import com.example.lab.model.Book;
import com.example.lab.model.Category;
import com.example.lab.service.BookService;
import com.example.lab.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private CategoryService categoryService;

    public BookController(BookService bookService, CategoryService categoryService){
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getBookPage(Model model){
        List<Book> books = this.bookService.findAll();
        model.addAttribute("book", books);
        return "books";
    }

    @GetMapping("/add-new")
    public String addNewBook(Model model){
        List<Category> category = this.categoryService.findAll();
        model.addAttribute("category", category);
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/{id}/edit")
    public String editBookPage(@PathVariable Long id, Model model){
        try{
            List<Category> categories = this.categoryService.findAll();
            Book book = this.bookService.findById(id);
            model.addAttribute("category", categories);
            model.addAttribute("book", book);
            return "add-book";
        }catch(RuntimeException ex){
            return "redirect:/books?error=" + ex.getMessage();
        }
    }

    @PostMapping
    public String save(@RequestParam MultipartFile image, @Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("category", categories);
            return "books";
        }try{
            this.bookService.save(book, image);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteWithPostMapping(@PathVariable Long id){
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id){
        this.bookService.deleteById(id);
        return "redirect:/books";
    }


}
