package com.example.BankService;

import com.example.BankService.services.contributionServices.ContributionServiceImpl;
import com.example.BankService.services.creditServices.CreditServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ContributionServiceTest {
    @Mock
    ContributionServiceImpl contributionService;
    @Test
    void checkCreditCalculations(){
        var credit = contributionService.calculateContr(10000, 6);
        Assertions.assertEquals(credit.getProfit(), 125000);
    }
}