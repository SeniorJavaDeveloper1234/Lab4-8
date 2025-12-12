package com.program.commands;

import com.program.bank.Deposit;
import com.program.manager.DepositManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewListDepositCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewListDepositCommand.class);

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

        logger.info("Виконання команди ViewListDeposit");

        for (Deposit deposit : depositmanager.getDeposits()) {
            System.out.println(deposit.toString());
        }

        logger.info("Відображено депозитів: {}", depositmanager.getDeposits().size());
    }
}
