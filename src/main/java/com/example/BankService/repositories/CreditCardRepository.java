package com.example.BankService.repositories;

import com.example.BankService.models.cards.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    CreditCard findByNumber(String number);
    void deleteByNumber(String number);

}
