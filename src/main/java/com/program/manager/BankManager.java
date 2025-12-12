package com.program.manager;

import com.program.bank.Bank;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BankManager {

    private static final Logger logger = LogManager.getLogger(BankManager.class);

    private final String BANKS_FILE_PATH = "./src/main/resources/bank/";
    @Getter
    private List<Bank> banks = new ArrayList<>();
    private final FileManager fileManager;

    public BankManager(FileManager fileManager) {
        this.fileManager = fileManager;
        logger.info("Створено BankManager. Починаю завантаження банків...");
        loadBanks();
    }

    public void addBank(Bank bank) {
        banks.add(bank);
        logger.info("Додано новий банк: {}", bank.getName());
    }

    private void loadBanks() {
        logger.info("Завантаження банків з {}", BANKS_FILE_PATH);

        banks.clear();
        File folder = new File(BANKS_FILE_PATH);

        if (!folder.exists() || !folder.isDirectory()) {
            logger.warn("Тека банків {} не існує або це не директорія", BANKS_FILE_PATH);
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null) {
            logger.warn("Не вдалося отримати список файлів із {}", BANKS_FILE_PATH);
            return;
        }

        for (File file : files) {
            Bank bank = FileManager.loadFromJson(file.getAbsolutePath(), Bank.class);
            if (bank != null) {
                banks.add(bank);
                logger.info("Завантажено банк: {}", bank.getName());
            } else {
                logger.error("Не вдалося завантажити банк із файлу {}", file.getName());
            }
        }

        logger.info("Завантажено банків: {}", banks.size());
    }

    public void saveAllBank() {
        logger.info("Збереження всіх банків...");

        File folder = new File(BANKS_FILE_PATH);
        if (!folder.exists())
            folder.mkdirs();

        for (Bank bank : banks) {
            String filePath = BANKS_FILE_PATH + bank.getName() + ".json";
            FileManager.saveAsJson(filePath, bank);
            logger.info("Файл банку {} збережено у {}", bank.getName(), filePath);
        }
    }

    public void saveBank(Bank bank) {
        logger.info("Збереження банку {}", bank.getName());

        File folder = new File(BANKS_FILE_PATH);
        if (!folder.exists())
            folder.mkdirs();

        String filePath = BANKS_FILE_PATH + bank.getName() + ".json";
        FileManager.saveAsJson(filePath, bank);
        logger.info("Файл банку {} збережено у {}", bank.getName(), filePath);
    }
}
