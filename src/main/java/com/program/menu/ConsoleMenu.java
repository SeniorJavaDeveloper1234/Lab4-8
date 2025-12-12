package com.program.menu;

import com.program.commands.*;
import com.program.manager.BankManager;
import com.program.manager.DepositManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ConsoleMenu {

    private static final Logger logger = LogManager.getLogger(ConsoleMenu.class);

    private Map<String, Command> commands = new HashMap<>();
    private final BankManager bankManager;
    private final DepositManager depositManager;
    private Scanner scanner;

    public ConsoleMenu(BankManager bankManager, DepositManager depositManager) {
        this.depositManager = depositManager;
        this.bankManager = bankManager;
        this.scanner = new Scanner(System.in);

        commands.put("add", new AddDepositCommand(bankManager, depositManager));
        commands.put("show", new ShowBankCommand(bankManager));
        commands.put("del", new DeleteDepositCommand(bankManager, depositManager));
        commands.put("search", new SearchDepositCommand(depositManager));
        commands.put("sort", new SortDepositCommand(depositManager));
        commands.put("view", new ViewListDepositCommand(depositManager));
        commands.put("help", new HelpCommand(commands));

        logger.info("Ініціалізовано ConsoleMenu та зареєстровано {} команд", commands.size());
    }

    public void run() {

        logger.info("Запуск консольного меню");

        System.out.println("Ласкаво просимо до консольної системи внесків!!!");
        System.out.println("Введіть команду help щоб побачити список усіх команд: ");

        while (true) {

            System.out.print(">>> ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            logger.info("Введена команда: {}", input);

            String[] commandArgs = input.split(" ", 2);
            String commandName = commandArgs[0];
            Command command = commands.get(commandName);
            String param = (commandArgs.length > 1) ? commandArgs[1] : "";

            if (command != null) {
                try {
                    command.execute(param);
                    logger.info("Команда {} виконана успішно", commandName);
                } catch (Exception e) {
                    logger.error("Помилка при виконанні команди {}", commandName, e);
                    System.out.println("Помилка при виконанні команди: " + e.getMessage());
                }
            } else if (Objects.equals(commandName, "exit")) {
                logger.info("Отримано команду exit. Завершення роботи програми");
                break;
            } else {
                logger.warn("Невідома команда: {}", commandName);
                System.out.println("Невідома команда! Введіть 'help' для списку.");
            }
        }

        logger.info("Консольне меню завершило роботу");
    }
}
