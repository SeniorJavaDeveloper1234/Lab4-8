package com.program.commands;

import com.program.bank.Bank;
import com.program.bank.Deposit;
import com.program.manager.BankManager;

import java.util.Scanner;

public class DeleteDepositCommand implements Command{

    private final BankManager bankmanager;

    public DeleteDepositCommand(BankManager bankManager) {
        this.bankmanager = bankManager;
    }

    @Override
    public String getDesc() {
        return "Видаляє депозит і повертає на накопичені кошти (del <bankname>)";
    }

    @Override
    public void execute(String param) {
        if(param.equals("")){
            System.out.println("Банк не вибрано");
        } else {
            Bank bank = null;

            for(Bank b : bankmanager.getBanks()){

                if(param.equals(b.getName())){
                    bank = b;
                    break;
                }
            }
            Scanner sc = new Scanner(System.in);

            int i = 0;



            for(Deposit d : bank.getDeposits()){
                System.out.println((i +1) + ". "+ d.toString());
                i++;
            }
            System.out.println("Введіть депозит який видалити: ");

            int depositIdx = sc.nextInt() - 1;

            Deposit deposit = bank.getDeposits().get(depositIdx);
            bank.getDeposits().remove(deposit);

            bankmanager.saveBank(bank);




        }
    }
}
