package com.example.BankService.models;

import com.example.BankService.models.cards.CreditCard;
import com.example.BankService.models.cards.DebitCard;
import com.example.BankService.models.contributions.Contribution;
import com.example.BankService.models.credits.SimpleCredit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "users_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String username;
    private String password;
    private Role role;
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<SimpleCredit> anyTargetCreditList;

    @OneToMany(mappedBy = "user")
    private List<DebitCard> debitCards;
    @OneToMany(mappedBy = "user")
    private List<CreditCard> creditCards;

    @OneToMany(mappedBy = "user")
    private List<Contribution> contributions;
}
