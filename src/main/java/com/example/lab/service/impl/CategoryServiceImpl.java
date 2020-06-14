package com.example.lab.service.impl;

import com.example.lab.model.Category;
import com.example.lab.model.exceptions.CategoryNotFoundException;
import com.example.lab.repository.CategoryRepository;
import com.example.lab.service.BookService;
import com.example.lab.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, BookService bookService) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException());
    }

    @Override
    public List<Category> findAllByName(String name) {
        return this.categoryRepository.findAllByName(name);
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category c = this.findById(id);
        category.setName(category.getName());
        category.setOpis(category.getOpis());
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateName(String name, Long id) {
        Category cat = this.findById(id);
        cat.setName(cat.getName());
        return this.categoryRepository.save(cat);
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
