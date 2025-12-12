package com.program.commands;

import com.program.bank.Deposit;
import com.program.manager.DepositManager;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewListDepositCommandTest {

    private DepositManager depositManager;
    private ViewListDepositCommand command;

    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        depositManager = mock(DepositManager.class);
        command = new ViewListDepositCommand(depositManager);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void testExecute_PrintsAllDeposits() {
        Deposit d1 = mock(Deposit.class);
        Deposit d2 = mock(Deposit.class);

        when(d1.toString()).thenReturn("DEP1");
        when(d2.toString()).thenReturn("DEP2");

        when(depositManager.getDeposits()).thenReturn(List.of(d1, d2));

        command.execute("");

        String out = output.toString();

        assertTrue(out.contains("DEP1"));
        assertTrue(out.contains("DEP2"));
    }
}
