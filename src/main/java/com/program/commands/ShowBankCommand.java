package com.program.commands;

import com.program.bank.Bank;
import com.program.manager.BankManager;

public class ShowBankCommand implements  Command {

    private final BankManager bankManager;

    public ShowBankCommand(BankManager bankManager) {
        this.bankManager = bankManager;
    }

    @Override
    public String getDesc() {
        return "Показує банки в які можна покласти гроші.";
    }

    @Override
    public void execute(String param) {
        for(Bank bank : bankManager.getBanks()){
            System.out.println(bank.getName());
            for(double d : bank.getDepositVariants()){
                System.out.println(d);
            }
        }
    }
}
