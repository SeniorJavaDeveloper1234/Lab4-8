package com.program;


import com.program.bank.Bank;
import com.program.manager.BankManager;
import com.program.manager.FileManager;
import com.program.menu.ConsoleMenu;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        FileManager fileManager = new FileManager();
        BankManager bankManager = new BankManager(fileManager);

        ConsoleMenu consoleMenu = new ConsoleMenu(fileManager, bankManager);
        consoleMenu.run();
    }
}
