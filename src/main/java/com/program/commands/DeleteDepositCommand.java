package com.program.commands;

import com.program.bank.Bank;
import com.program.bank.Deposit;
import com.program.manager.BankManager;
import com.program.manager.DepositManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class DeleteDepositCommand implements Command {

    private static final Logger logger = LogManager.getLogger(DeleteDepositCommand.class);

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

        logger.info("Виконання команди DeleteDeposit з параметром: {}", param);

        if (param.equals("")) {
            logger.warn("Команда DeleteDeposit викликана без назви банку");
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
            logger.warn("Банк {} не знайдено", param);
            System.out.println("Банк '" + param + "' не знайдено!");
            return;
        }

        if (bank.getDeposits().isEmpty()) {
            logger.info("Спроба видалення депозиту з банку {}, але депозитів немає", bank.getName());
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
            logger.warn(
                    "Невірний індекс депозиту {} для банку {}",
                    depositIdx,
                    bank.getName()
            );
            System.out.println("Невірний номер депозиту!");
            return;
        }

        Deposit deposit = bank.getDeposits().get(depositIdx);

        bank.getDeposits().remove(deposit);
        depositmanager.deleteDeposit(deposit);
        bank.deleteDeposit(deposit);

        bankmanager.saveBank(bank);
        depositmanager.loadDeposits();

        logger.info(
                "Депозит видалено: id={}, owner={}, bank={}, amount={}",
                deposit.getDepositId(),
                deposit.getOwnerName(),
                deposit.getBankName(),
                deposit.getAmount()
        );

        System.out.println("Депозит успішно видалено!");
    }
}
