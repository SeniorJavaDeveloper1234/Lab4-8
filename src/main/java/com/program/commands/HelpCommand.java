package com.program.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class HelpCommand implements Command {

    private static final Logger logger = LogManager.getLogger(HelpCommand.class);

    private Map<String, Command> allCommands;

    public HelpCommand(Map<String, Command> allCommands){
        this.allCommands = allCommands;
    }

    @Override
    public String getDesc() {
        return "виводить списоку усіх команд.";
    }

    @Override
    public void execute(String param) {

        logger.info("Виконання команди Help");

        System.out.println("Банк не вказаний");

        System.out.println("====Список всіх команд====");
        for (Map.Entry<String, Command> e : allCommands.entrySet()) {
            System.out.println(e.getKey() + " - " + e.getValue().getDesc());
        }
        System.out.println();
    }
}
