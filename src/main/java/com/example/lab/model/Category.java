package com.example.lab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String detail;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    List<Book> books;

    public Category() {}

    public Category(Long id, String name, String detail){
        this.id = id;
        this.name = name;
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpis() {
        return detail;
    }

    public void setOpis(String detail) {
        this.detail = detail;
    }
}
