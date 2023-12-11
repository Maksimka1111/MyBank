package com.example.BankService.models.cards;

import com.example.BankService.models.Currencies;
import com.example.BankService.models.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
public class DebitCard {
    @Id
    @SequenceGenerator(name = "debitCard_seq", sequenceName = "debitCard_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "debitCard_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String number;
    private String ownerName;
    private String date;
    private String secretCode;
    private double money;
    private Currencies currency;
    @Nullable
    private boolean blocked;


    @ManyToOne
    @JoinColumn(name="user_id")
    User user;
}
