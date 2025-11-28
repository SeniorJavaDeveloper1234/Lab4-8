package com.program.commands;

import com.program.bank.Deposit;
import com.program.manager.BankManager;
import com.program.manager.DepositManager;

public class ViewListDepositCommand implements Command{

    private final DepositManager depositmanager;

    public ViewListDepositCommand(DepositManager depositmanager) {
        this.depositmanager = depositmanager;
    }


    @Override
    public String getDesc() {
        return "Відображає депозити у всіх банках";
    }

    @Override
    public void execute(String param) {
        depositmanager.loadDeposits();
        for(Deposit deposit : depositmanager.getDeposits()){
            System.out.println(deposit.toString());
        }
    }
}
