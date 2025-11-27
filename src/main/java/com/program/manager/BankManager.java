package com.program.manager;

import com.program.bank.Bank;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class BankManager {

    private final String BANKS_FILE_PATH = "./src/main/resources/bank/";
    @Getter
    private List<Bank> banks = new ArrayList<Bank>();

    public BankManager(){

    }

    public void addBank(Bank bank) {
        banks.add(bank);
    }



}
