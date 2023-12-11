package com.example.BankService.services.cardServices;

import com.example.BankService.models.Currencies;
import com.example.BankService.models.cards.CreditCard;
import com.example.BankService.models.cards.DebitCard;
import com.example.BankService.repositories.CreditCardRepository;
import com.example.BankService.repositories.DebitCardRepository;
import com.example.BankService.services.UserServiceImpl;
import com.example.BankService.services.contributionServices.ContributionServiceImpl;
import com.example.BankService.services.creditServices.CreditService;
import com.example.BankService.services.creditServices.CreditServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    DebitCardRepository debitCardRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    CreditServiceImpl creditService;
    @Autowired
    ContributionServiceImpl contributionService;
    @Autowired
    UserServiceImpl userService;

    private String generateNumber(){
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();
        // 16 знаков
        for(int i = 0; i < 4; i++)
            cardNumber.append(String.valueOf(random.nextInt(1000, 9999))).append(" ");

        return cardNumber.toString();
    };
    String generateSecretCode(){
        Random random = new Random();

        return String.valueOf(random.nextInt(100, 999));
    }
    String generateTerm(){
        Date date = new Date();
        String str = String.valueOf(date.getMonth() + 1) + "/";
        str += String.valueOf(date.getYear() + 5).substring(1);
        return str;
    }
    @Override
    public void issueCard(DebitCard card) {
        String s = generateNumber();
        while (debitCardRepository.findByNumber(s) != null)
        {
            s = generateNumber();
        }
        card.setNumber(s);
        card.setSecretCode(generateSecretCode());
        card.setDate(generateTerm());
        card.setUser(userService.getCurrentUser());
        card.setOwnerName(userService.getCurrentUser().getUsername());
        if (card.getOwnerName().equals("admin"))
            card.setMoney(9999999);
        userService.addCard(card);
        debitCardRepository.save(card);
    }

    @Override
    public void issueCard(CreditCard card) {
        String s = generateNumber();
        while (creditCardRepository.findByNumber(s) != null)
        {
            s = generateNumber();
        }
        card.setNumber(s);
        card.setSecretCode(generateSecretCode());
        card.setDate(generateTerm());
        card.setUser(userService.getCurrentUser());
        card.setOwnerName(userService.getCurrentUser().getUsername());
        userService.addCard(card);
        creditCardRepository.save(card);
    }

    @Override
    public List<DebitCard> showDebitCards() {
        return debitCardRepository.findAll();
    }

    @Override
    public List<CreditCard> showCreditCards() {
        return creditCardRepository.findAll();
    }

    @Override
    public double calculatePercent(int limit, int salary) {
        if (salary > 20000 && salary < 40000 && limit > 20000 && limit < 30000)
            return 25 * 1.5;
        else if (salary > 40000 && limit > 30000)
            return 15 * 1.5;
        else if (salary > 10000 && salary < 20000 && limit < 20000)
            return 20 * 1.5;
        return 0;
    }

    @Override
    public boolean extendPeriod(String cardNumber, int months, String type) {
        if (months > 36)
            return false;
        if (type.equals("Debit")) {
            var card = debitCardRepository.findByNumber(cardNumber);

            String[] date = card.getDate().split("/");

            date[1] = String.valueOf(Integer.parseInt(date[1]) + months/12);
            date[0] = String.valueOf(Integer.parseInt(date[0]) + months%12);

            card.setDate(date[0] + "/" + date[1]);
            return true;
        }
        var card = creditCardRepository.findByNumber(cardNumber);

        String[] date = card.getDate().split("/");

        date[1] = String.valueOf(Integer.parseInt(date[1]) + months/12);
        date[0] = String.valueOf(Integer.parseInt(date[0]) + months%12);

        card.setDate(date[0] + "/" + date[1]);
        return true;
    }
    @Override
    public void block(String cardNumber) {
        if (debitCardRepository.findByNumber(cardNumber) == null){
            var card = creditCardRepository.findByNumber(cardNumber);
            card.setBlocked(true);
            return;
        }
        var card  = debitCardRepository.findByNumber(cardNumber);
        card.setBlocked(true);
    }

    @Override
    public boolean makeTransfer(String cardNumber, String otherCardNumber, double money) {
        otherCardNumber += " ";
        if (debitCardRepository.findByNumber(cardNumber) == null){
            var sendCard = creditCardRepository.findByNumber(cardNumber);
            if (sendCard.getLimit() + sendCard.getDuty() < money)
                return false;
            else{
                sendCard.setDuty(sendCard.getDuty() + money);
            }
        } else{
            var sendCard = debitCardRepository.findByNumber(cardNumber);
            if (sendCard.getMoney() < money)
                return false;
            else{
                sendCard.setMoney(sendCard.getMoney() - money);
            }
        }
        if (creditService.getCredit(otherCardNumber) != null){
            creditService.getCredit(otherCardNumber).setSum((int) (creditService.getCredit(otherCardNumber).getSum() - money));
        }
        if (contributionService.getContr(otherCardNumber) != null){
            contributionService.getContr(otherCardNumber).setSum((int) (contributionService.getContr(otherCardNumber).getSum() + money));
        }
        if (debitCardRepository.findByNumber(otherCardNumber) == null){
            var sendCard = creditCardRepository.findByNumber(otherCardNumber);
            sendCard.setDuty(sendCard.getDuty() - money);
        } else{
            var sendCard = debitCardRepository.findByNumber(cardNumber);
                sendCard.setMoney(sendCard.getMoney() + money);
        }
        debitCardRepository.flush();
        creditCardRepository.flush();
        return true;
    }

    @Override
    @Transactional
    public boolean deleteCard(String cardNumber) {
        if (debitCardRepository.findByNumber(cardNumber) == null){
            userService.delCard(creditCardRepository.findByNumber(cardNumber));
            creditCardRepository.deleteByNumber(cardNumber);
            return true;
        }
        userService.delCard(debitCardRepository.findByNumber(cardNumber));
        debitCardRepository.deleteByNumber(cardNumber);
        return true;
    }

    @Override
    public boolean isTransferAvailable(String cardNumber, double money) {
        if (debitCardRepository.findByNumber(cardNumber) == null)
        {
            var card = creditCardRepository.findByNumber(cardNumber);
            if (card.getLimit() - card.getDuty() < money)
                return false;
            else {
                card.setDuty(card.getDuty() + money);
                return true;
            }
        }
        var card = debitCardRepository.findByNumber(cardNumber);
        if (card.getMoney() < money)
            return false;
        else card.setMoney(card.getMoney() - money);
        return true;
    }
}
