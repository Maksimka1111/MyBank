package com.example.BankService.services.creditServices;

import com.example.BankService.models.contributions.Contribution;
import com.example.BankService.models.credits.SimpleCredit;
import com.example.BankService.repositories.SimpleCreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CreditServiceImpl implements CreditService {
    @Autowired
    SimpleCreditRepository simpleCreditRepository;

    private String generateNumber(){
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();
        // 16 знаков
        for(int i = 0; i < 4; i++)
            cardNumber.append(String.valueOf(random.nextInt(1000, 9999)));

        return cardNumber.toString();
    };

    @Override
    public void applyCredit() {
        var list = simpleCreditRepository.findAll();
        for(SimpleCredit simpleCredit: list){
            if (!simpleCredit.getConfirmed()) {
                simpleCredit.setConfirmed(true);
                simpleCreditRepository.saveAndFlush(simpleCredit);
                return;
            }
        }
    }

    @Override
    public SimpleCredit calculateCredit(int sum, int term) {
        double percent = 0;

        if (sum >= 10000 && sum < 300000){
            if (term == 3)
                percent = 15 * 1.05;
            else if (term <=12) {
                percent = (12 + term) * 1.05;
            }
            else {
                percent = 25.2;
            }
        }
        else if (sum >= 300000 && sum < 1000000){
            if (term == 3)
                percent = 15 * 1.025;
            else if (term <=12) {
                percent = (12 + term) * 1.025;
            }
            else {
                percent = 24.6;
            }
        }
        SimpleCredit simpleCredit = new SimpleCredit();
        simpleCredit.setNumber(generateNumber());
        simpleCredit.setTerm(term);
        simpleCredit.setSum(sum);
        simpleCredit.setPercent(percent);
        simpleCredit.setMonthPayment((double) (sum / term) +  sum * ((percent/100) / 12));
        simpleCredit.setConfirmed(false);
        simpleCreditRepository.save(simpleCredit);
        return simpleCredit;
    }

    @Override
    public SimpleCredit getCalculatedCredit() {
        var list = simpleCreditRepository.findAll();
        for(SimpleCredit simpleCredit: list){
            if (!simpleCredit.getConfirmed()) {
                return simpleCredit;
            }
        }
        return null;
    }

    @Override
    public SimpleCredit getCredit(String number) {
        return simpleCreditRepository.findByNumber(number);
    }

    @Override
    public boolean checkAccess(int salary) {
        return !(getCalculatedCredit().getMonthPayment() + getCalculatedCredit().getMonthPayment() * 0.2 > salary);
    }

    @Override
    public void refuseCredit() {
        var list = simpleCreditRepository.findAll();
        for(SimpleCredit simpleCredit: list){
            if (!simpleCredit.getConfirmed()) {
                simpleCreditRepository.delete(simpleCreditRepository.findByNumber(simpleCredit.getNumber()));
                return;
            }
        }
    }
}
