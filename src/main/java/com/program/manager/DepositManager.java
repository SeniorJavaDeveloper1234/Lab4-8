package com.program.manager;

import com.program.bank.Bank;
import com.program.bank.Deposit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class DepositManager {

    private final BankManager bankManager;
    @Getter
    private List<Deposit> deposits = new ArrayList<>();

    public DepositManager(BankManager bankManager){
        this.bankManager = bankManager;
        loadDeposits();
    }

    public void sort(String field) {
        switch (field.toLowerCase()) {
            case "id"       -> deposits.sort(Comparator.comparingInt(Deposit::getDepositId));
            case "owner"    -> deposits.sort(Comparator.comparing(Deposit::getOwnerName));
            case "bank"     -> deposits.sort(Comparator.comparing(Deposit::getBankName));
            case "amount"   -> deposits.sort(Comparator.comparingDouble(Deposit::getAmount));
            case "rate"     -> deposits.sort(Comparator.comparingDouble(Deposit::getInterestRate));
            case "duration" -> deposits.sort(Comparator.comparingInt(Deposit::getDurationMonths));
            default -> System.out.println("Невідомий параметр сортування!");
        }
    }


    public void addDeposit(Deposit deposit){
        deposits.add(deposit);
    }

    public void deleteDeposit(Deposit deposit){
        deposits.remove(deposit);
    }

    public void loadDeposits() {
        deposits.clear();
        for (Bank bank : bankManager.getBanks()) {
            deposits.addAll(bank.getDeposits());
        }
    }


    public int generateId(){

        while(true){
            Random random = new Random();
            int code = 100000 + random.nextInt(900000);

            boolean isExist = false;

            for(Deposit deposit : deposits){
                if(code == deposit.getDepositId()){
                    isExist = true;
                    break;
                }
            }

            if(!isExist){
                return code;
            }
        }

    }

}
