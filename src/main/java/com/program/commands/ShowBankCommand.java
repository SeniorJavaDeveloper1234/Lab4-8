package com.program.commands;

import com.program.bank.Bank;
import com.program.manager.BankManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowBankCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowBankCommand.class);

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

        logger.info("Виконання команди ShowBank");

        for (Bank bank : bankManager.getBanks()) {
            System.out.println(bank.getName());
            for (double d : bank.getDepositVariants()) {
                System.out.println(d);
            }
        }

        logger.info("Виведено список банків: {}", bankManager.getBanks().size());
    }
}
