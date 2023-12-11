package com.example.BankService.repositories;

import com.example.BankService.models.cards.CreditCard;
import com.example.BankService.models.cards.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
    DebitCard findByNumber(String number);
    void deleteByNumber(String number);
}
