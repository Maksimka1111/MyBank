package com.example.BankService.controllers;

import com.example.BankService.services.creditServices.CreditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credit")
public class CreditController {
    @Autowired
    CreditServiceImpl creditService;
    @GetMapping("/credits")
    public String getCredits(){
        return "credits/credits";
    }

    @GetMapping("/makeCredit")
    public String getPageOfMakeCredit(Model model){
        return "credits/makeCredit";
    }
    @PostMapping("/makeCredit")
    public String makeCredit(@ModelAttribute("sum") int sum, @ModelAttribute("term") int term, Model model){
        creditService.calculateCredit(sum, term);
        return "redirect:/credit/getCreditCalc";
    }

    @GetMapping("/getCreditCalc")
    public String getPageOfCreditCard(Model model){
        model.addAttribute("credit", creditService.getCalculatedCredit());
        return "credits/creditCalc";
    }
    @PostMapping("/getCreditCalc")
    public String calcCredit(Model model){
        return "redirect:/credit/confirmCredit";
    }

    @GetMapping("/confirmCredit")
    public String getPageOfConfirmCreditCard(Model model){
        return "credits/confirmCredit";
    }
    @PostMapping("/confirmCredit")
    public String confirmCredit(@ModelAttribute("salary") int salary, Model model){
        if (creditService.checkAccess(salary)) {
            creditService.applyCredit();
            return "redirect:/profile";
        }
        model.addAttribute("creditError", "Кредит не одобрен");
        return "credits/confirmCredit";
    }

    @PostMapping("/refuseCredit")
    public String refuseCredit(Model model){
        creditService.refuseCredit();
        return "redirect:/profile";
    }
}
