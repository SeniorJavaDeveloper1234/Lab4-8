package com.program.commands;

import com.program.bank.Bank;
import com.program.manager.BankManager;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowBankCommandTest {

    private BankManager bankManager;
    private ShowBankCommand command;
    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        bankManager = mock(BankManager.class);
        command = new ShowBankCommand(bankManager);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void testExecute_DisplaysAllBanksAndVariants() {

        Bank bank1 = mock(Bank.class);
        when(bank1.getName()).thenReturn("Mono");
        when(bank1.getDepositVariants()).thenReturn(new double[]{5.0, 7.5});

        Bank bank2 = mock(Bank.class);
        when(bank2.getName()).thenReturn("Privat");
        when(bank2.getDepositVariants()).thenReturn(new double[]{4.2});

        when(bankManager.getBanks()).thenReturn(List.of(bank1, bank2));

        command.execute("");

        String out = output.toString();

        assertTrue(out.contains("Mono"));
        assertTrue(out.contains("Privat"));

        assertTrue(out.contains("5.0"));
        assertTrue(out.contains("7.5"));
        assertTrue(out.contains("4.2"));
    }
}
