package com.program.menu;

import com.program.commands.Command;
import com.program.manager.BankManager;
import com.program.manager.DepositManager;
import org.junit.jupiter.api.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleMenuTest {

    private BankManager bankManager;
    private DepositManager depositManager;
    private ConsoleMenu menu;

    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() throws Exception {
        bankManager = mock(BankManager.class);
        depositManager = mock(DepositManager.class);

        menu = new ConsoleMenu(bankManager, depositManager);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    // ----------------------- TEST 1 ---------------------------
    @Test
    void testRun_HelpCommandAndExit() throws Exception {
        // Симулюємо три введення:
        // help
        // exit
        String fakeInput = "help\nexit\n";
        Scanner fakeScanner = new Scanner(fakeInput);

        // Підмінюємо scanner у ConsoleMenu через reflection
        Field scannerField = ConsoleMenu.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(menu, fakeScanner);

        menu.run();

        String printed = output.toString();

        assertTrue(printed.contains("Ласкаво просимо"));
        assertTrue(printed.contains("help -")); // друк команд з HelpCommand
    }

    // ----------------------- TEST 2 ---------------------------
    @Test
    void testRun_UnknownCommand() throws Exception {
        String fakeInput = "abc\nexit\n";
        Scanner fakeScanner = new Scanner(fakeInput);

        Field scannerField = ConsoleMenu.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(menu, fakeScanner);

        menu.run();

        String printed = output.toString();

        assertTrue(printed.contains("Невідома команда"));
    }
}
