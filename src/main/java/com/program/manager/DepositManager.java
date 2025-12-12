package com.program.manager;

import com.program.bank.Bank;
import com.program.bank.Deposit;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class DepositManager {

    private static final Logger logger = LogManager.getLogger(DepositManager.class);

    private final BankManager bankManager;
    @Getter
    private List<Deposit> deposits = new ArrayList<>();

    public DepositManager(BankManager bankManager){
        this.bankManager = bankManager;
        logger.info("Створено DepositManager. Завантаження депозитів...");
        loadDeposits();
    }

    public void sort(String field) {
        logger.info("Сортування депозитів за параметром: {}", field);

        switch (field.toLowerCase()) {
            case "id"       -> deposits.sort(Comparator.comparingInt(Deposit::getDepositId));
            case "owner"    -> deposits.sort(Comparator.comparing(Deposit::getOwnerName));
            case "bank"     -> deposits.sort(Comparator.comparing(Deposit::getBankName));
            case "amount"   -> deposits.sort(Comparator.comparingDouble(Deposit::getAmount));
            case "rate"     -> deposits.sort(Comparator.comparingDouble(Deposit::getInterestRate));
            case "duration" -> deposits.sort(Comparator.comparingInt(Deposit::getDurationMonths));
            default -> {
                logger.warn("Невідомий параметр сортування: {}", field);
                System.out.println("Невідомий параметр сортування!");
            }
        }
    }

    public void addDeposit(Deposit deposit){
        deposits.add(deposit);
        logger.info(
                "Додано депозит: id={}, owner={}, bank={}, amount={}",
                deposit.getDepositId(),
                deposit.getOwnerName(),
                deposit.getBankName(),
                deposit.getAmount()
        );
    }

    public void deleteDeposit(Deposit deposit){
        deposits.remove(deposit);
        logger.info(
                "Видалено депозит: id={}, owner={}, bank={}",
                deposit.getDepositId(),
                deposit.getOwnerName(),
                deposit.getBankName()
        );
    }

    public void loadDeposits() {
        logger.info("Завантаження депозитів з банків");
        deposits.clear();

        for (Bank bank : bankManager.getBanks()) {
            deposits.addAll(bank.getDeposits());
        }

        logger.info("Завантажено депозитів: {}", deposits.size());
    }

    public int generateId(){
        logger.info("Генерація унікального ID для депозиту");

        while(true){
            Random random = new Random();
            int code = 100000 + random.nextInt(900000);

            boolean isExist = false;

            for(Deposit deposit : deposits){
                if(code == deposit.getDepositId()){
                    isExist = true;
                    break;
                }
            }

            if(!isExist){
                logger.info("Згенеровано унікальний ID: {}", code);
                return code;
            }
        }
    }
}
