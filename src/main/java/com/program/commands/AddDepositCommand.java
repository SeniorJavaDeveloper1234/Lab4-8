package com.program.commands;

import com.program.bank.Bank;
import com.program.bank.Deposit;
import com.program.manager.BankManager;
import com.program.manager.DepositManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class AddDepositCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddDepositCommand.class);

    private final BankManager bankmanager;
    private final DepositManager depositmanager;

    public AddDepositCommand(BankManager bankmanager, DepositManager depositmanager) {
        this.bankmanager = bankmanager;
        this.depositmanager = depositmanager;
    }

    @Override
    public String getDesc() {
        return "Додає депозит у вказаний банк (add <bankname>)";
    }

    @Override
    public void execute(String param) {

        logger.info("Виконання команди AddDeposit з параметром: {}", param);

        if (param.isEmpty()) {
            logger.warn("Команда AddDeposit викликана без назви банку");
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
            System.out.println("Банк \"" + param + "\" не знайдено!");
            return;
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Введіть ваше ім'я:");
        String ownerName = sc.nextLine();

        System.out.println("Введіть скільки коштів ви хочете внести:");
        double amount = sc.nextDouble();

        System.out.println("Доступні ставки:");
        for (int i = 0; i < bank.getDepositVariants().length; i++) {
            System.out.println((i + 1) + ". " + bank.getDepositVariants()[i]);
        }

        System.out.println("Виберіть ставку депозиту:");
        int interestIdx = sc.nextInt() - 1;

        if (interestIdx < 0 || interestIdx >= bank.getDepositVariants().length) {
            logger.warn("Неправильний вибір ставки депозиту для банку {}", bank.getName());
            System.out.println("Неправильний вибір ставки.");
            return;
        }

        double interest = bank.getDepositVariants()[interestIdx];

        System.out.println("Введіть тривалість у місяцях:");
        int durationMonths = sc.nextInt();

        int id = depositmanager.generateId();

        Deposit deposit = new Deposit(ownerName, amount, interest, durationMonths, bank.getName(), id);
        bank.addDeposit(deposit);

        bankmanager.saveBank(bank);
        depositmanager.loadDeposits();

        logger.info(
                "Депозит успішно додано: id={}, owner={}, bank={}, amount={}, rate={}, duration={}",
                id, ownerName, bank.getName(), amount, interest, durationMonths
        );

        System.out.println("Депозит успішно додано!");
    }
}
