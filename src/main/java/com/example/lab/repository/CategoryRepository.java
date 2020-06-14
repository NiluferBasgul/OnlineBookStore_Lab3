package com.example.lab.repository;

import com.example.lab.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository{
    List<Category> findAll();
    Optional<Category> findById(Long id);
    List<Category> findAllByName(String name);
    Category save(Category category);
    void deleteById(Long id);
}
