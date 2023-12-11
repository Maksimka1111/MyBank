package com.example.BankService.repositories;

import com.example.BankService.models.credits.SimpleCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleCreditRepository extends JpaRepository<SimpleCredit, String> {
    SimpleCredit findByNumber(String number);
}
