package com.example.BankService.controllers;

import com.example.BankService.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@PreAuthorize("hasAnyRole('USER', 'EMPLOYEE','ADMIN')")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @GetMapping("/profile")
    public String profile(Model model){
        model.addAttribute("DebCards", userService.getCurrentUser().getDebitCards());
        model.addAttribute("CredCards", userService.getCurrentUser().getCreditCards());
        model.addAttribute("Credits", userService.getCurrentUser().getAnyTargetCreditList());
        model.addAttribute("Contrs", userService.getCurrentUser().getContributions());
        return "profile";
    }
}
