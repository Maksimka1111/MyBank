package com.example.BankService.controllers;

import com.example.BankService.models.User;
import com.example.BankService.services.userDetails.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping("/loginM")
    public String loginPage(Model model) {
        model.addAttribute("activePage", "loginM");
        return "loginM";
    }

    @PostMapping("/loginM")
    public String loginUser(@ModelAttribute("userForm") @Validated User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println(1);
            return "loginM";
        }
        if (!userService.findUser(userForm.getUsername())) {
            model.addAttribute("userError", "Неверное имя пользователя или пароль");
            return "loginM";
        }
        return "redirect:/personalArea";
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Validated User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userService.saveUser(userForm)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}