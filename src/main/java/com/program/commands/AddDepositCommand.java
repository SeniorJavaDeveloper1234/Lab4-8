package com.program.commands;

import com.program.bank.Bank;
import com.program.bank.Deposit;
import com.program.manager.BankManager;

import java.util.Scanner;

public class AddDepositCommand implements Command {

    private BankManager bankmanager;

    public AddDepositCommand(BankManager bankmanager){
        this.bankmanager = bankmanager;
    }

    @Override
    public String getDesc() {
        return "Додає депозит у вказаний банк (add <bankname>)";
    }

    @Override
    public void execute(String param) {

        Bank bank = new Bank();

        for(Bank b : bankmanager.getBanks()){
            if(param == b.getName()){
                bank = b;
                break;
            }
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Введіть ваше ім'я:");
        String ownerName = sc.nextLine();
        System.out.println("Введіть скікльки коштів вихочете внести:");
        double amount = sc.nextDouble();
        System.out.println("Введіть скікльки коштів вихочете внести:");
    }
}
