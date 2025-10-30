package com.program.commands;

import java.util.Map;

public class HelpCommand implements Command{

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

        System.out.println("====Список всіх команд====");
        for(Map.Entry<String,Command> e : allCommands.entrySet()){
            System.out.println(e.getKey() + " - " + e.getValue().getDesc());
        }
        System.out.println();
    }
}
