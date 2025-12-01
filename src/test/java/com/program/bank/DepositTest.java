package com.program.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepositTest {

    @Test
    void testConstructorAndGetters() {
        Deposit d = new Deposit("Vova", 1000.0, 5.5, 12, "Mono", 123456);

        assertEquals("Vova", d.getOwnerName());
        assertEquals(1000.0, d.getAmount());
        assertEquals(5.5, d.getInterestRate());
        assertEquals(12, d.getDurationMonths());
        assertEquals("Mono", d.getBankName());
        assertEquals(123456, d.getDepositId());
    }

    @Test
    void testToString_NotNull() {
        Deposit d = new Deposit("Vova", 1000.0, 5.5, 12, "Mono", 123456);
        assertNotNull(d.toString());
        assertFalse(d.toString().isEmpty());
    }
}
