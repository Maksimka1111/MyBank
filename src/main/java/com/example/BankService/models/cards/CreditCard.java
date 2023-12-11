package com.example.BankService.models.cards;

import com.example.BankService.models.Currencies;
import com.example.BankService.models.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
public class CreditCard {
    @Id
    @SequenceGenerator(name = "creditCard_seq", sequenceName = "creditCard_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "creditCard_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String number;
    private String ownerName;
    private String date;
    private String secretCode;
    private double duty;
    private int limit;
    private double percent;
    private Currencies currency;
    @Nullable
    private boolean blocked;


    @ManyToOne
    @JoinColumn(name="user_id")
    User user;
}
