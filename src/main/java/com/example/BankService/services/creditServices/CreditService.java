package com.example.BankService.services.creditServices;

import com.example.BankService.models.contributions.Contribution;
import com.example.BankService.models.credits.SimpleCredit;

public interface CreditService {
    void applyCredit();
    SimpleCredit calculateCredit(int sum, int term);
    void refuseCredit();
    SimpleCredit getCalculatedCredit();
    SimpleCredit getCredit(String number);
    boolean checkAccess(int salary);
}
