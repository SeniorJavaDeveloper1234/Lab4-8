package com.program.bank;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @Test
    void testConstructorAndGetters() {
        double[] variants = {5.5, 7.3};

        Bank bank = new Bank("Mono", variants);

        assertEquals("Mono", bank.getName());
        assertArrayEquals(variants, bank.getDepositVariants());
        assertNotNull(bank.getDeposits());
        assertTrue(bank.getDeposits().isEmpty());
    }

    @Test
    void testAddDeposit() {
        Bank bank = new Bank("Mono", new double[]{5.5});

        Deposit deposit = new Deposit("Vova", 1000, 5.5, 12, "Mono", 111111);

        bank.addDeposit(deposit);

        assertEquals(1, bank.getDeposits().size());
        assertEquals(deposit, bank.getDeposits().get(0));
    }

    @Test
    void testToString_NotNull() {
        Bank bank = new Bank("Mono", new double[]{5.5});
        assertNotNull(bank.toString());
        assertFalse(bank.toString().isEmpty());
    }
}
