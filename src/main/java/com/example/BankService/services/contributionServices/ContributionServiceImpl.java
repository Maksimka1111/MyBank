package com.example.BankService.services.contributionServices;

import com.example.BankService.models.contributions.Contribution;
import com.example.BankService.models.credits.SimpleCredit;
import com.example.BankService.repositories.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ContributionServiceImpl implements ContributionService {
    @Autowired
    ContributionRepository contributionRepository;
    private String generateNumber(){
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();
        // 16 знаков
        for(int i = 0; i < 4; i++)
            cardNumber.append(String.valueOf(random.nextInt(1000, 9999)));

        return cardNumber.toString();
    };
    @Override
    public void applyContr() {
        var list = contributionRepository.findAll();
        for(Contribution contribution: list){
            if (!contribution.getConfirmed()) {
                contribution.setConfirmed(true);
                contributionRepository.saveAndFlush(contribution);
                return;
            }
        }
    }

    @Override
    public Contribution calculateContr(int sum, int term) {
        Contribution contribution = new Contribution();
        Map<Integer, Double> percents = new HashMap<>();
        percents.put(1, 6.5);
        percents.put(2, 10.5);
        percents.put(3, 10.6);
        percents.put(4, 11.7);
        percents.put(5, 11.8);
        percents.put(6, 12.5);
        percents.put(9, 11.4);
        percents.put(12, 8.3);
        contribution.setNumber(generateNumber());
        contribution.setSum(sum);
        contribution.setTerm(term);
        contribution.setPercent(percents.get(term));
        contribution.setVisibility(true);
        contribution.setProfit((int) (sum + percents.get(term)/100 * sum));
        contribution.setConfirmed(false);
        contributionRepository.save(contribution);
        return contribution;
    }

    @Override
    public void refuseContr() {
        var list = contributionRepository.findAll();
        for(Contribution contribution: list){
            if (!contribution.getConfirmed()) {
                contributionRepository.delete(contribution);
                return;
            }
        }
    }

    @Override
    public Contribution getContr(String number) {
        return contributionRepository.findByNumber(number);
    }

    @Override
    public Contribution getCalculatedContr() {
        var list = contributionRepository.findAll();
        for(Contribution contribution: list){
            if (!contribution.getConfirmed()) {
                return contribution;
            }
        }
        return null;
    }
}
