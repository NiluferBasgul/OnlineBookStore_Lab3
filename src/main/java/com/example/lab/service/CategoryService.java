package com.example.lab.service;

import com.example.lab.model.Category;

import java.util.List;


public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    List<Category> findAllByName(String name);
    Category save(Category category);
    Category update(Long id, Category category);
    Category updateName(String name, Long id);
    void deleteById(Long id);
}
