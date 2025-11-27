package com.program.bank;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    @Getter
    private List<Deposit> deposits = new ArrayList<>();
    @Getter
    private String name;
    @Getter
    private double[] depositVariants = new double[3];

    public Bank(){}
    public Bank(String name){
        this.name = name;
    }

    public void addDeposit(Deposit deposit) {
        deposits.add(deposit);
    }

    public void deleteDeposit(Deposit deposit) {
        deposits.remove(deposit);
    }


}
