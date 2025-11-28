package com.program.commands;

import com.program.manager.DepositManager;

public class SortDepositCommand implements Command{

    private final DepositManager depositManager;

    public SortDepositCommand(DepositManager depositManager) {
        this.depositManager = depositManager;
    }


    @Override
    public String getDesc() {
        return "Сортує депозити всіх банків за командами (id, owner, duration, rate, bank)";
    }

    @Override
    public void execute(String param) {
        if (param.isEmpty()) {
            System.out.println("Метод сортування не заданий!!!");
        } else {
            depositManager.sort(param);
        }
    }
}
