package com.program.commands;

import com.program.bank.Bank;
import com.program.bank.Deposit;
import com.program.manager.BankManager;
import com.program.manager.DepositManager;

import java.util.Scanner;

public class DeleteDepositCommand implements Command{

    private final BankManager bankmanager;
    private final DepositManager depositmanager;

    public DeleteDepositCommand(BankManager bankManager, DepositManager depositmanager) {
        this.bankmanager = bankManager;
        this.depositmanager = depositmanager;
    }

    @Override
    public String getDesc() {
        return "Видаляє депозит і повертає на накопичені кошти (del <bankname>)";
    }

    @Override
    public void execute(String param) {
        if (param.equals("")) {
            System.out.println("Банк не вибрано");
            return;
        }

        Bank bank = null;
        for (Bank b : bankmanager.getBanks()) {
            if (param.equalsIgnoreCase(b.getName())) {
                bank = b;
                break;
            }
        }

        if (bank == null) {
            System.out.println("Банк '" + param + "' не знайдено!");
            return;
        }

        if (bank.getDeposits().isEmpty()) {
            System.out.println("У цьому банку немає депозитів.");
            return;
        }

        System.out.println("Список депозитів:");
        for (int i = 0; i < bank.getDeposits().size(); i++) {
            System.out.println((i + 1) + ". " + bank.getDeposits().get(i));
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Введіть номер депозиту для видалення: ");

        int depositIdx = sc.nextInt() - 1;

        if (depositIdx < 0 || depositIdx >= bank.getDeposits().size()) {
            System.out.println("Невірний номер депозиту!");
            return;
        }

        Deposit deposit = bank.getDeposits().get(depositIdx);
        bank.getDeposits().remove(deposit);
        depositmanager.deleteDeposit(deposit);

        bankmanager.saveBank(bank);
        depositmanager.loadDeposits();
        System.out.println("Депозит успішно видалено!");
    }
}
