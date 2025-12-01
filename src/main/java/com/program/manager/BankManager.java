package com.program.manager;

import com.program.bank.Bank;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BankManager {

    private final String BANKS_FILE_PATH = "./src/main/resources/bank/";
    @Getter
    private List<Bank> banks = new ArrayList<>();
    private final FileManager fileManager;

    public BankManager(FileManager fileManager) {
        this.fileManager = fileManager;

        loadBanks();
    }

    public BankManager(FileManager fileManager, boolean skipLoad) {
        this.fileManager = fileManager;
        if (!skipLoad) {
            loadBanks();
        }
    }

    protected File getFolder() {
        return new File(BANKS_FILE_PATH);
    }

    public void addBank(Bank bank) {
        banks.add(bank);
    }

    public void loadBanks() {
        banks.clear();

        File folder = new File(BANKS_FILE_PATH);

        if (!folder.exists() || !folder.isDirectory())
            return;

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null) return;

        for (File file : files) {
            Bank bank = fileManager.loadFromJson(file.getAbsolutePath(), Bank.class);
            if (bank != null) {
                banks.add(bank);
            }
        }
    }

    public void saveAllBank() {

        File folder = new File(BANKS_FILE_PATH);
        if (!folder.exists())
            folder.mkdirs();

        for (Bank bank : banks) {
            String filePath = BANKS_FILE_PATH + bank.getName() + ".json";
            fileManager.saveAsJson(filePath, bank);
        }
    }

    public void saveBank(Bank bank) {

        File folder = new File(BANKS_FILE_PATH);
        if (!folder.exists())
            folder.mkdirs();

        String filePath = BANKS_FILE_PATH + bank.getName() + ".json";
        fileManager.saveAsJson(filePath, bank);
    }


}
