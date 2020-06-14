package com.example.lab.repository.impl;

import com.example.lab.model.Category;
import com.example.lab.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository{

    private HashMap<Long, Category> category;
    private AtomicLong counter;


    public CategoryRepositoryImpl(){
        this.category = new HashMap<>();
        this.counter = new AtomicLong(0L);
        Category c1 = new Category(5L, "Lord of the Rings", "Advanture");
        this.category.put(c1.getId(), c1);
        Category c2 = new Category(9L, "Harry Potter", "Fantasy");
        this.category.put(c2.getId(), c2);
       // Manufacturer m1 = new Manufacturer(1L, "m1");
//        Manufacturer m2 = new Manufacturer(2L, "m2");
//        this.manufacturers.put(m1.getId(), m1);
//        this.manufacturers.put(m2.getId(), m2);
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(this.category.values());
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(this.category.get(id));
    }

    @Override
    public List<Category> findAllByName(String name) {
        return this.category.values()
                .stream()
                .filter(item -> item.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public Category save(Category category) {
        if(category.getId() == null){
            category.setId(this.counter.getAndIncrement());
        }
        this.category.put(category.getId(), category);
        return category;
    }

    @Override
    public void deleteById(Long id) {
        this.category.remove(id);
    }
}
