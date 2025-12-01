package com.program.commands;

import com.program.bank.Deposit;
import com.program.manager.DepositManager;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchDepositCommandTest {

    private DepositManager depositManager;
    private SearchDepositCommand command;
    private ByteArrayOutputStream output;

    private Deposit dep1;

    @BeforeEach
    void setUp() {
        depositManager = mock(DepositManager.class);
        command = new SearchDepositCommand(depositManager);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        dep1 = mock(Deposit.class);
        when(dep1.getDepositId()).thenReturn(111);
        when(dep1.getOwnerName()).thenReturn("Vova");
        when(dep1.getBankName()).thenReturn("Mono");
        when(dep1.getDurationMonths()).thenReturn(12);
        when(dep1.getInterestRate()).thenReturn(7.5);
        when(dep1.getAmount()).thenReturn(10000.0);

        when(dep1.toString()).thenReturn("DEP1");

        when(depositManager.getDeposits()).thenReturn(List.of(dep1));
    }

    @AfterEach
    void restore() {
        System.setOut(System.out);
    }

    // ========== BASIC ERRORS ==========

    @Test
    void testEmptyParam() {
        command.execute("");

        assertTrue(output.toString().contains("Не введено жодного параметру"));
    }

    @Test
    void testWrongFormat() {
        command.execute("id");

        assertTrue(output.toString().contains("Формат: search"));
    }

    @Test
    void testUnknownField() {
        command.execute("abc 123");

        assertTrue(output.toString().contains("Невідомий параметр пошуку"));
    }

    // ========== SEARCH BY ID ==========

    @Test
    void testSearchByIdFound() {
        command.execute("id 111");

        assertTrue(output.toString().contains("DEP1"));
    }

    @Test
    void testSearchByIdWrongNumber() {
        command.execute("id abc");

        assertTrue(output.toString().contains("ID повинен бути числом"));
    }

    @Test
    void testSearchByIdNotFound() {
        command.execute("id 222");

        assertTrue(output.toString().contains("Нічого не знайдено"));
    }

    // ========== SEARCH BY OWNER ==========

    @Test
    void testSearchByOwnerFound() {
        command.execute("owner Vova");

        assertTrue(output.toString().contains("DEP1"));
    }

    @Test
    void testSearchByOwnerNotFound() {
        command.execute("owner Petro");

        assertTrue(output.toString().contains("Нічого не знайдено"));
    }

    // ========== SEARCH BY BANK ==========

    @Test
    void testSearchByBankFound() {
        command.execute("bank Mono");

        assertTrue(output.toString().contains("DEP1"));
    }

    @Test
    void testSearchByBankNotFound() {
        command.execute("bank Privat");

        assertTrue(output.toString().contains("Нічого не знайдено"));
    }

    // ========== SEARCH BY DURATION ==========

    @Test
    void testSearchByDurationFound() {
        command.execute("duration 12");

        assertTrue(output.toString().contains("DEP1"));
    }

    @Test
    void testSearchByDurationWrongNumber() {
        command.execute("duration abc");

        assertTrue(output.toString().contains("duration має бути числом"));
    }

    @Test
    void testSearchByDurationNotFound() {
        command.execute("duration 36");

        assertTrue(output.toString().contains("Нічого не знайдено"));
    }

    // ========== SEARCH BY RATE ==========

    @Test
    void testSearchByRateFound() {
        command.execute("rate 7.5");

        assertTrue(output.toString().contains("DEP1"));
    }

    @Test
    void testSearchByRateWrongNumber() {
        command.execute("rate qqq");

        assertTrue(output.toString().contains("Ставка має бути числом"));
    }

    @Test
    void testSearchByRateNotFound() {
        command.execute("rate 10.0");

        assertTrue(output.toString().contains("Нічого не знайдено"));
    }

    // ========== SEARCH BY AMOUNT ==========

    @Test
    void testSearchByAmountFound() {
        command.execute("amount 10000");

        assertTrue(output.toString().contains("DEP1"));
    }

    @Test
    void testSearchByAmountWrongNumber() {
        command.execute("amount ab");

        assertTrue(output.toString().contains("Сума має бути числом"));
    }

    @Test
    void testSearchByAmountNotFound() {
        command.execute("amount 999");

        assertTrue(output.toString().contains("Нічого не знайдено"));
    }
}
