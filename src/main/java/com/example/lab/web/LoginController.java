package com.example.lab.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    private List<String> allow_names;

    @PostConstruct
    public void init() {
        this.allow_names = new ArrayList<>();
        this.allow_names.add("nilufer");
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }
}
