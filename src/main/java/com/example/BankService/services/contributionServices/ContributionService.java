package com.example.BankService.services.contributionServices;

import com.example.BankService.models.contributions.Contribution;
import com.example.BankService.models.credits.SimpleCredit;

public interface ContributionService {
    void applyContr();
    Contribution calculateContr(int sum, int term);
    void refuseContr();
    Contribution getContr(String number);
    Contribution getCalculatedContr();
}
