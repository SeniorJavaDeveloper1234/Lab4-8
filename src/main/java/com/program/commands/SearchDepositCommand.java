package com.program.commands;

import com.program.bank.Deposit;
import com.program.manager.DepositManager;

public class SearchDepositCommand implements Command {

    private final DepositManager depositManager;

    public SearchDepositCommand(DepositManager depositManager) {
        this.depositManager = depositManager;
    }

    @Override
    public String getDesc() {
        return "шукає депозит за параметром (id, owner, duration, rate, amount, bank)";
    }

    @Override
    public void execute(String param) {
        if (param.isEmpty()) {
            System.out.println("Не введено жодного параметру!");
            return;
        }

        String[] parts = param.split(" ", 2);

        if (parts.length < 2) {
            System.out.println("Формат: search <field> <value>");
            return;
        }

        String field = parts[0].toLowerCase();
        String value = parts[1];

        boolean found = false;

        for (Deposit d : depositManager.getDeposits()) {
            switch (field) {

                case "id" -> {
                    try {
                        int id = Integer.parseInt(value);
                        if (d.getDepositId() == id) {
                            System.out.println(d);
                            found = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID повинен бути числом!");
                        return;
                    }
                }

                case "owner" -> {
                    if (d.getOwnerName().equalsIgnoreCase(value)) {
                        System.out.println(d);
                        found = true;
                    }
                }

                case "bank" -> {
                    if (d.getBankName().equalsIgnoreCase(value)) {
                        System.out.println(d);
                        found = true;
                    }
                }

                case "duration" -> {
                    try {
                        int m = Integer.parseInt(value);
                        if (d.getDurationMonths() == m) {
                            System.out.println(d);
                            found = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("duration має бути числом!");
                        return;
                    }
                }

                case "rate" -> {
                    try {
                        double r = Double.parseDouble(value);
                        if (d.getInterestRate() == r) {
                            System.out.println(d);
                            found = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ставка має бути числом!");
                        return;
                    }
                }

                case "amount" -> {
                    try {
                        double a = Double.parseDouble(value);
                        if (d.getAmount() == a) {
                            System.out.println(d);
                            found = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Сума має бути числом!");
                        return;
                    }
                }

                default -> {
                    System.out.println("Невідомий параметр пошуку!");
                    System.out.println("Можна шукати за: id, owner, duration, rate, amount, bank");
                    return;
                }
            }
        }

        if (!found) {
            System.out.println("Нічого не знайдено!");
        }
    }
}
