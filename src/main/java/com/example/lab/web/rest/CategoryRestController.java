package com.example.lab.web.rest;

import com.example.lab.model.Category;
import com.example.lab.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/categories")
public class CategoryRestController {

    private CategoryService categoryService;

    public CategoryRestController() {}

    public CategoryRestController(CategoryService categoryService){ this.categoryService = categoryService;}

    @GetMapping
    public List<Category> findAll(@RequestParam(required = false) String name){
        if(name == null){
            this.categoryService.findAll();
        }
        return this.categoryService.findAllByName(name);
    }

    @GetMapping("/by-name")
    public List<Category> findAllByName(@RequestParam String name){
        return this.categoryService.findAllByName(name);
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id){
        return this.categoryService.findById(id);
    }

    @PostMapping
    public Category save(@Valid Category category){
        return this.categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @Valid Category category){
        return this.categoryService.update(id, category);
    }

    @PatchMapping("/{id}")
    public Category updateName(@PathVariable Long id, @RequestParam String name){
        return this.categoryService.updateName(name, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        this.categoryService.deleteById(id);
    }



}
