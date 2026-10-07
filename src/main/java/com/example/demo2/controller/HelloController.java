package com.example.demo2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(
            @RequestParam(value = "name", defaultValue = "World") String name,
            @RequestParam(value = "lang", defaultValue = "es") String lang) {

        String saludo = "Hola";
        if ("en".equalsIgnoreCase(lang) || "us".equalsIgnoreCase(lang)) saludo = "Hello";
        else if ("fr".equalsIgnoreCase(lang)) saludo = "Bonjour";
        else if ("de".equalsIgnoreCase(lang)) saludo = "Hallo";

        return String.format("%s %s!", saludo, name);
    }
}
