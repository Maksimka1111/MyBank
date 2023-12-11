package com.example.BankService.repositories;

import com.example.BankService.models.cards.CreditCard;
import com.example.BankService.models.contributions.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, String> {
    Contribution findByNumber(String number);
}
