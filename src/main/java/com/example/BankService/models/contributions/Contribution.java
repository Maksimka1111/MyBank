package com.example.BankService.models.contributions;

import com.example.BankService.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contribution {
    @Id
    private String number;
    private double sum;
    private double percent;
    private int term;
    int profit;
    private boolean visibility;
    private boolean confirmed;

    public boolean getConfirmed(){
        return this.confirmed;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;
}
