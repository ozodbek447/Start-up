package com.example.testwebb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class Controller {

    @GetMapping("/admin")
    public String admin() {
        return "index";
    }
}
