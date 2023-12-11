package com.example.BankService.services;


import com.example.BankService.models.User;
import com.example.BankService.models.cards.CreditCard;
import com.example.BankService.models.cards.DebitCard;

public interface UserService {
    void addCredit();
    void addCard(DebitCard debitCard);
    void addCard(CreditCard creditCard);

    void delCard(DebitCard card);
    void delCard(CreditCard card);
    void makePayment();
    void fillProfile(String phoneNumber, String email);
    User findUser(String username);
    User getCurrentUser();
}
