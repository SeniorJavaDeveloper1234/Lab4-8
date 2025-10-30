package com.program.bank;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    List<Deposit> deposits = new ArrayList<>();

    public void addDeposit(Deposit deposit) {
        deposits.add(deposit);
    }

    public void deleteDeposit(Deposit deposit) {
        deposits.remove(deposit);
    }
}
