package com.program.bank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Deposit {
    private String ownerName;
    private double amount;
    private double interestRate;
    private int durationMonths;
    private String bankName;
    private int depositId;

    public Deposit(){}

    @Override
    public String toString(){
        return " Унікальний ітендифікатор: "  + depositId  + "; Назва банку: " + bankName + "; Сума вкладу: " + amount + "; Місячна ставка: " + durationMonths + "; Ім'я вкладника: " + ownerName ;
    }
}
