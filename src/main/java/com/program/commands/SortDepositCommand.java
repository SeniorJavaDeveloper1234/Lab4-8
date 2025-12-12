package com.program.commands;

import com.program.manager.DepositManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SortDepositCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SortDepositCommand.class);

    private final DepositManager depositManager;

    public SortDepositCommand(DepositManager depositManager) {
        this.depositManager = depositManager;
    }

    @Override
    public String getDesc() {
        return "Сортує депозити всіх банків за параметрами (id, owner, duration, rate, bank, amount)";
    }

    @Override
    public void execute(String param) {

        logger.info("Виконання команди SortDeposit з параметром: {}", param);

        if (param.isEmpty()) {
            logger.warn("Команда SortDeposit викликана без параметра сортування");
            System.out.println("Метод сортування не заданий!!!");
        } else {
            depositManager.sort(param);
            logger.info("Сортування депозитів виконано за параметром: {}", param);
        }
    }
}
