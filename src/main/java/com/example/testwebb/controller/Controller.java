package com.example.testwebb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping
public class Controller {

    @GetMapping("/admin")
    public String admin() {
        return "index";
    }
}
