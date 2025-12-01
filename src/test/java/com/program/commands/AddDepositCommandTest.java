package com.program.commands;

import com.program.bank.Bank;
import com.program.bank.Deposit;
import com.program.manager.BankManager;
import com.program.manager.DepositManager;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddDepositCommandTest {

    private BankManager bankManager;
    private DepositManager depositManager;
    private AddDepositCommand command;

    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        bankManager = mock(BankManager.class);
        depositManager = mock(DepositManager.class);
        command = new AddDepositCommand(bankManager, depositManager);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void testExecute_NoBankNameProvided() {
        command.execute("");

        String printed = output.toString().trim();
        assertTrue(printed.contains("Банк не вибрано"));
    }

    @Test
    void testExecute_BankNotFound() {
        when(bankManager.getBanks()).thenReturn(List.of());

        command.execute("mono");

        String printed = output.toString().trim();
        assertTrue(printed.contains("не знайдено"));
    }

    @Test
    void testExecute_AddDepositSuccessfully() {

        Bank bank = mock(Bank.class);
        when(bank.getName()).thenReturn("mono");
        when(bank.getDepositVariants()).thenReturn(new double[]{5.0, 7.5});
        when(bankManager.getBanks()).thenReturn(List.of(bank));

        when(depositManager.generateId()).thenReturn(123456);


        String input = """
                Vova
                1000
                1
                12
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));


        command.execute("mono");


        ArgumentCaptor<Deposit> captor = ArgumentCaptor.forClass(Deposit.class);
        verify(bank).addDeposit(captor.capture());

        Deposit dep = captor.getValue();

        assertEquals("Vova", dep.getOwnerName());
        assertEquals(1000, dep.getAmount());
        assertEquals(5.0, dep.getInterestRate());
        assertEquals(12, dep.getDurationMonths());
        assertEquals("mono", dep.getBankName());
        assertEquals(123456, dep.getDepositId());

        verify(bankManager).saveBank(bank);
        verify(depositManager).loadDeposits();

        assertTrue(output.toString().contains("Депозит успішно додано!"));
    }

    @Test
    void testExecute_InvalidRateIndex() {
        Bank bank = mock(Bank.class);
        when(bank.getName()).thenReturn("mono");
        when(bank.getDepositVariants()).thenReturn(new double[]{3.0});
        when(bankManager.getBanks()).thenReturn(List.of(bank));

        String input = """
                Name
                1000
                5
                """;

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        command.execute("mono");

        assertTrue(output.toString().contains("Неправильний вибір ставки."));
    }
}
