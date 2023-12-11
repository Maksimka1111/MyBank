package com.example.BankService.controllers;

import com.example.BankService.services.contributionServices.ContributionServiceImpl;
import com.example.BankService.services.creditServices.CreditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contribution")
public class ContributionController {
    @Autowired
    ContributionServiceImpl contributionService;

    @GetMapping("/contributions")
    public String getContributions(){
        return "contribution/contributions";
    }
    @GetMapping("/makeContribution")
    public String getPageOfMakeCredit(Model model){
        return "contribution/makeContr";
    }
    @PostMapping("/makeContribution")
    public String makeCredit(@ModelAttribute("sum") int sum, @ModelAttribute("term") int term, Model model){
        contributionService.calculateContr(sum, term);
        return "redirect:/contribution/getContrCalc";
    }

    @GetMapping("/getContrCalc")
    public String getPageOfCreditCard(Model model){
        model.addAttribute("contr", contributionService.getCalculatedContr());
        return "contribution/CalcContr";
    }
    @PostMapping("/getContrCalc")
    public String calcCredit(Model model){
        return "redirect:/contribution/confirmContribution";
    }

    @GetMapping("/confirmContribution")
    public String getPageOfConfirmCreditCard(Model model){
        return "contribution/confirmContr";
    }
    @PostMapping("/confirmContribution")
    public String confirmCredit(Model model){
        contributionService.applyContr();
        return "redirect:/profile";
    }

    @PostMapping("/refuseContr")
    public String refuseCredit(Model model){
        contributionService.refuseContr();
        return "redirect:/profile";
    }
}
