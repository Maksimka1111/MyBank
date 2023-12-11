package com.example.BankService.controllers;

import com.example.BankService.models.Currencies;
import com.example.BankService.models.cards.CreditCard;
import com.example.BankService.models.cards.DebitCard;
import com.example.BankService.services.cardServices.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cards")
@PreAuthorize("hasAnyRole('USER', 'EMPLOYEE', 'ADMIN')")
public class CardController {
    @Autowired
    CardService cardService;

    @GetMapping("/makeDebitCard")
    public String getPageOfCreateDebitCard(Model model){
        return "cards/makeDebitCard";
    }
    @PostMapping("/makeDebitCard")
    public String createDebitCard(@ModelAttribute("currency") Currencies currency, Model model){
        DebitCard debitCard = new DebitCard();
        debitCard.setCurrency(currency);
        debitCard.setBlocked(false);
        model.addAttribute("card", debitCard);
        return "redirect:/cards/debitConfirm";
    }

    @GetMapping("/debitConfirm")
    public String getPageOfConfirmDebitCard(Model model){
        DebitCard debitCard = new DebitCard();
        debitCard.setCurrency(Currencies.RUB);
        cardService.issueCard(debitCard);
        model.addAttribute("card", debitCard);
        return "cards/debitConfirm";
    }
    @PostMapping("/debitConfirm")
    public String confirmDebitCard(Model model){
        return "redirect:/profile";
    }

    @GetMapping("/makeCreditCard")
    public String getPageOfCreateCreditCard(Model model){
        return "cards/makeCreditCard";
    }
    @PostMapping("/makeCreditCard")
    public String createCreditCard(@ModelAttribute("currency") Currencies currency, @ModelAttribute("limit") int limit, @ModelAttribute("salary") int salary, Model model){
        CreditCard creditCard = new CreditCard();
        creditCard.setCurrency(currency);
        creditCard.setLimit(limit);
        creditCard.setPercent(cardService.calculatePercent(limit, salary));
        model.addAttribute("card", creditCard);
        return "redirect:/cards/creditCardConfirm";
    }
    @GetMapping("/creditCardConfirm")
    public String getPageOfConfirmCreditCard(Model model){
        CreditCard card = new CreditCard();
        card.setCurrency(Currencies.RUB);
        card.setPercent(15*1.5);
        model.addAttribute("card", card);
        return "cards/creditCardConfirm";
    }
    @PostMapping("/сreditCardConfirm")
    public String confirmCreditCard(Model model){
        return "redirect:/profile";
    }
    @PostMapping("/delCard")
    public String delCard(@ModelAttribute("cardNumber") String cardNumber){
        cardService.deleteCard(cardNumber);
        return "redirect:/profile";
    }
    @PostMapping("/blockCard")
    public String blockCard(@ModelAttribute("cardNumber") String cardNumber){
        cardService.block(cardNumber);
        return "redirect:/profile";
    }
    @GetMapping("/makeTransfer")
    public String getMakeTransfer(@ModelAttribute("cardNumber") String cardNumber, Model model){
        model.addAttribute("yourCardNumber", cardNumber);
        return "cards/makeTransfer";
    }
    @PostMapping("/makeTransfer")
    public String makeTransfer(@ModelAttribute("yourCardNumber") String cardNumber,@ModelAttribute("cardTransfer") String cardTransfer,@ModelAttribute("money") String money, Model model){
        if (!cardService.isTransferAvailable(cardNumber, Double.parseDouble(money)))
        {
            model.addAttribute("errorMessage", "Недостаточно средств");
            return "cards/makeTransfer";
        }
        cardService.makeTransfer(cardNumber, cardTransfer, Double.parseDouble(money));
        return "redirect:/profile";
    }
}
