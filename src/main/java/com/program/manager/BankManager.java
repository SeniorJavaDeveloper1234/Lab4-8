package com.program.manager;

import com.program.bank.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankManager {

    private List<Bank> banks = new ArrayList<Bank>();

    public BankManager(){

    }

    public void addBank(Bank bank) {
        banks.add(bank);
    }
}
