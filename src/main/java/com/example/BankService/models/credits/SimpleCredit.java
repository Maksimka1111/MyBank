package com.example.BankService.models.credits;

import com.example.BankService.models.User;
import jakarta.annotation.Nullable;
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
public class SimpleCredit {
    @Id
    private String number;
    private int sum;
    private double percent;
    private int term;
    private double monthPayment;
    private boolean confirmed;

    public boolean getConfirmed(){
        return this.confirmed;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;
}
