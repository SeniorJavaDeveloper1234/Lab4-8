package com.program;


import com.program.bank.Bank;
import com.program.manager.BankManager;
import com.program.manager.DepositManager;
import com.program.manager.FileManager;
import com.program.menu.ConsoleMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args){

        FileManager fileManager = new FileManager();
        BankManager bankManager = new BankManager(fileManager);
        DepositManager depositManager = new DepositManager(bankManager);


        ConsoleMenu consoleMenu = new ConsoleMenu(bankManager, depositManager);
        consoleMenu.run();
    }
}
