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

        if(param.equals("")){
            System.out.println("Банк не вибрано");
        } else{
            Bank bank = null;

            for(Bank b : bankmanager.getBanks()){

                if(param.equals(b.getName())){
                    bank = b;
                    break;
                }
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("Введіть ваше ім'я:");
            String ownerName = sc.nextLine();
            System.out.println("Введіть скікльки коштів вихочете внести:");
            double amount = sc.nextDouble();
            for(int i = 0; i < bank.getDepositVariants().length; i++){
                System.out.println((i + 1) + ". " + bank.getDepositVariants()[i]);
            }
            System.out.println("Виберіть ставку депозиту: ");
            int interestIdx =  sc.nextInt() - 1;
            double interest = bank.getDepositVariants()[interestIdx];
            System.out.println("Введіть триваліть у місяцях");
            int durationMonths = sc.nextInt();

            Deposit deposit = new Deposit(ownerName, amount, interest, durationMonths, bank.getName());
            bank.addDeposit(deposit);
            bankmanager.saveBank(bank);
        }
    }
}
