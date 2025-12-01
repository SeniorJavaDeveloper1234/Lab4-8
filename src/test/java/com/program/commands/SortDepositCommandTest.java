package com.program.commands;

import com.program.manager.DepositManager;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SortDepositCommandTest {

    private DepositManager depositManager;
    private SortDepositCommand command;

    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        depositManager = mock(DepositManager.class);
        command = new SortDepositCommand(depositManager);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    // ---------- TEST 1: empty parameter ----------
    @Test
    void testExecute_EmptyParam() {
        command.execute("");

        String out = output.toString();
        assertTrue(out.contains("Метод сортування не заданий!!!"));

        verify(depositManager, never()).sort(any());
    }

    // ---------- TEST 2: valid parameter ----------
    @Test
    void testExecute_ValidParam() {
        command.execute("amount");

        verify(depositManager).sort("amount");
    }
}
