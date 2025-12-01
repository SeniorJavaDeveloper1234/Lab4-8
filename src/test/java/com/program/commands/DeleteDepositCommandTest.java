package com.program.commands;

import com.program.bank.Bank;
import com.program.bank.Deposit;
import com.program.manager.BankManager;
import com.program.manager.DepositManager;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteDepositCommandTest {

    private BankManager bankManager;
    private DepositManager depositManager;
    private DeleteDepositCommand command;

    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        bankManager = mock(BankManager.class);
        depositManager = mock(DepositManager.class);
        command = new DeleteDepositCommand(bankManager, depositManager);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    // -------------------------
    // TEST 1 — No bank name
    // -------------------------
    @Test
    void testExecute_NoBankProvided() {
        command.execute("");

        String printed = output.toString();
        assertTrue(printed.contains("Банк не вибрано"));
    }

    // -------------------------
    // TEST 2 — Bank not found
    // -------------------------
    @Test
    void testExecute_BankNotFound() {
        when(bankManager.getBanks()).thenReturn(List.of());

        command.execute("Mono");

        assertTrue(output.toString().contains("не знайдено"));
    }

    // ------------------------------
    // TEST 3 — Bank exists but empty
    // ------------------------------
    @Test
    void testExecute_NoDepositsInBank() {
        Bank bank = mock(Bank.class);
        when(bank.getName()).thenReturn("Mono");
        when(bank.getDeposits()).thenReturn(List.of());

        when(bankManager.getBanks()).thenReturn(List.of(bank));

        command.execute("Mono");

        assertTrue(output.toString().contains("немає депозитів"));
    }

    // -------------------------------------------------
    // TEST 4 — Invalid index (out of range)
    // -------------------------------------------------
    @Test
    void testExecute_InvalidDepositIndex() {
        Deposit dep = mock(Deposit.class);

        Bank bank = mock(Bank.class);
        when(bank.getName()).thenReturn("Mono");
        when(bank.getDeposits()).thenReturn(List.of(dep));

        when(bankManager.getBanks()).thenReturn(List.of(bank));

        // enter "5" (invalid index)
        System.setIn(new ByteArrayInputStream("5\n".getBytes()));

        command.execute("Mono");

        assertTrue(output.toString().contains("Невірний номер депозиту"));
    }

    // -------------------------------------------------
    // TEST 5 — Successful deletion
    // -------------------------------------------------
    @Test
    void testExecute_SuccessfulDeletion() {
        Deposit dep = mock(Deposit.class);

        // mutable list for delete()
        List<Deposit> depositList = new java.util.ArrayList<>();
        depositList.add(dep);

        Bank bank = mock(Bank.class);
        when(bank.getName()).thenReturn("Mono");
        when(bank.getDeposits()).thenReturn(depositList);

        when(bankManager.getBanks()).thenReturn(List.of(bank));

        // user selects deposit index 1
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));

        command.execute("Mono");

        // deposit must be removed
        assertEquals(0, depositList.size());

        // verify deletion logic
        verify(depositManager).deleteDeposit(dep);
        verify(bankManager).saveBank(bank);
        verify(depositManager).loadDeposits();

        assertTrue(output.toString().contains("Депозит успішно видалено"));
    }
}
