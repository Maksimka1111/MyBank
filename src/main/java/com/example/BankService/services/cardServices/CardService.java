package com.example.BankService.services.cardServices;

import com.example.BankService.models.cards.CreditCard;
import com.example.BankService.models.cards.DebitCard;

import java.util.List;

public interface CardService {
    void issueCard(DebitCard card);
    void issueCard(CreditCard card);
    List<DebitCard> showDebitCards();
    List<CreditCard> showCreditCards();
    double calculatePercent(int limit, int salary);
    boolean extendPeriod(String cardNumber, int months, String type);
    void block(String cardNumber);
    boolean makeTransfer(String cardNumber, String otherCardNumber, double money);
    boolean deleteCard(String cardNumber);
    boolean isTransferAvailable(String cardNumber, double money);
}
