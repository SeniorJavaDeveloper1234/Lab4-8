package com.program.bank;

public class Deposit {
    private String ownerName;
    private double amount;
    private double interestRate;
    private int durationMonths;
    private Bank bank;

    public Deposit(String ownerName, double amount, double interestRate, int durationMonths, Bank bank) {
        this.ownerName = ownerName;
        this.amount = amount;
        this.interestRate = interestRate;
        this.durationMonths = durationMonths;
        this.bank = bank;
    }

}
