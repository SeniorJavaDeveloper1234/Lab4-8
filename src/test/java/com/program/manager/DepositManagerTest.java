package com.program.manager;

import com.program.bank.Bank;
import com.program.bank.Deposit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepositManagerTest {

    private BankManager bankManager;
    private DepositManager depositManager;

    @BeforeEach
    void setUp() {
        bankManager = mock(BankManager.class);
        depositManager = new DepositManager(bankManager);
    }

    // -----------------------------------------
    // loadDeposits()
    // -----------------------------------------
    @Test
    void testLoadDeposits() {
        Bank b1 = mock(Bank.class);
        Bank b2 = mock(Bank.class);

        Deposit d1 = new Deposit();
        Deposit d2 = new Deposit();
        Deposit d3 = new Deposit();

        when(b1.getDeposits()).thenReturn(List.of(d1, d2));
        when(b2.getDeposits()).thenReturn(List.of(d3));
        when(bankManager.getBanks()).thenReturn(List.of(b1, b2));

        depositManager.loadDeposits();

        assertEquals(3, depositManager.getDeposits().size());
    }

    // -----------------------------------------
    // addDeposit()
    // -----------------------------------------
    @Test
    void testAddDeposit() {
        Deposit dep = new Deposit();
        depositManager.addDeposit(dep);

        assertEquals(1, depositManager.getDeposits().size());
        assertTrue(depositManager.getDeposits().contains(dep));
    }

    // -----------------------------------------
    // deleteDeposit()
    // -----------------------------------------
    @Test
    void testDeleteDeposit() {
        Deposit dep = new Deposit();
        depositManager.addDeposit(dep);

        depositManager.deleteDeposit(dep);

        assertEquals(0, depositManager.getDeposits().size());
    }

    // -----------------------------------------
    // sort() by id
    // -----------------------------------------
    @Test
    void testSortById() {
        Deposit d1 = new Deposit(); d1.setDepositId(300);
        Deposit d2 = new Deposit(); d2.setDepositId(100);
        Deposit d3 = new Deposit(); d3.setDepositId(200);

        depositManager.addDeposit(d1);
        depositManager.addDeposit(d2);
        depositManager.addDeposit(d3);

        depositManager.sort("id");

        assertEquals(100, depositManager.getDeposits().get(0).getDepositId());
    }

    // -----------------------------------------
    // sort() by amount
    // -----------------------------------------
    @Test
    void testSortByAmount() {
        Deposit d1 = new Deposit(); d1.setAmount(500);
        Deposit d2 = new Deposit(); d2.setAmount(100);
        Deposit d3 = new Deposit(); d3.setAmount(300);

        depositManager.addDeposit(d1);
        depositManager.addDeposit(d2);
        depositManager.addDeposit(d3);

        depositManager.sort("amount");

        assertEquals(100, depositManager.getDeposits().get(0).getAmount());
    }

    // -----------------------------------------
    // sort() unknown parameter
    // -----------------------------------------
    @Test
    void testSortUnknown() {
        depositManager.sort("something"); // should not throw errors
        assertTrue(true); // just verify no exception thrown
    }

    // -----------------------------------------
    // generateId()
    // -----------------------------------------
    @Test
    void testGenerateIdUnique() {
        Deposit d1 = new Deposit(); d1.setDepositId(123456);
        Deposit d2 = new Deposit(); d2.setDepositId(999999);

        depositManager.addDeposit(d1);
        depositManager.addDeposit(d2);

        int id = depositManager.generateId();

        // ID must be 6 digits
        assertTrue(id >= 100000 && id <= 999999);

        // ID must NOT match existing
        assertNotEquals(123456, id);
        assertNotEquals(999999, id);
    }
}
