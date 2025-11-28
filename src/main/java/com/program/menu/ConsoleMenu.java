package com.program.menu;

import com.program.commands.*;
import com.program.manager.BankManager;
import com.program.manager.FileManager;

import java.util.*;

public class ConsoleMenu {

    private Map<String, Command> commands = new HashMap<>();
    private final BankManager bankManager;
    private final FileManager fileManager;
    private Scanner scanner;

    public ConsoleMenu(FileManager fileManager, BankManager bankManager) {
        this.fileManager = fileManager;
        this.bankManager =  bankManager;
        this.scanner = new Scanner(System.in);

        commands.put("add", new AddDepositCommand(bankManager));
        commands.put("showb", new ShowBankCommand(bankManager));
        commands.put("del", new DeleteDepositCommand(bankManager));
        commands.put("search", new SearchDepositCommand());
        commands.put("sort", new SortDepositCommand());
        commands.put("view", new ViewListDepositCommand());
        commands.put("help", new HelpCommand(commands));

    }

    public void run(){
        System.out.println("Ласкаво просимо до консольної системи внесків!!!");
        System.out.println("Введіть команду help щоб побачити список усіх команд: ");

        while (true) {

            System.out.print(">>> ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] commandArgs = input.split(" ", 2);
            String commandName = commandArgs[0];
            Command command = commands.get(commandName);
            String param = (commandArgs.length > 1) ? commandArgs[1] : "";

            if (command != null) {
                try {
                    command.execute(param);
                } catch (Exception e) {
                    System.out.println("Помилка при виконанні команди: " + e.getMessage());
                }
            } else if (Objects.equals(commandName, "exit")) {
                System.exit(0);
            } else {
                System.out.println("Невідома команда! Введіть 'help' для списку.");
            }
        }

    }



}
