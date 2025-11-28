package com.program.bank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Deposit {
    private String ownerName;
    private double amount;
    private double interestRate;
    private int durationMonths;
    private Bank bank;


    public Deposit(){}

}
