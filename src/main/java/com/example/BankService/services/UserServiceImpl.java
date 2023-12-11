package com.example.BankService.services;

import com.example.BankService.models.Role;
import com.example.BankService.models.User;
import com.example.BankService.models.cards.CreditCard;
import com.example.BankService.models.cards.DebitCard;
import com.example.BankService.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public void addCredit() {

    }

    @Override
    public void addCard(DebitCard debitCard) {
        getCurrentUser().getDebitCards().add(debitCard);
        userRepository.flush();
    }

    @Override
    public void addCard(CreditCard creditCard) {
        getCurrentUser().getCreditCards().add(creditCard);
        userRepository.flush();
    }

    @Override
    public void delCard(DebitCard card) {
        getCurrentUser().getDebitCards().remove(card);
        userRepository.flush();
    }

    @Override
    public void delCard(CreditCard card) {
        getCurrentUser().getCreditCards().remove(card);
        userRepository.flush();
    }

    @Override
    public void makePayment() {

    }

    @Override
    public void fillProfile(String phoneNumber, String email) {
        getCurrentUser().setPhoneNumber(phoneNumber);
        getCurrentUser().setEmail(email);
        userRepository.saveAndFlush(getCurrentUser());
    }
    @Override
    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName());
    }
}
