package com.example.BankService.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/")
    public String mainPage(){
        return "index";
    }
    @GetMapping("/info")
    public String info(){
        return "info";
    }
}
