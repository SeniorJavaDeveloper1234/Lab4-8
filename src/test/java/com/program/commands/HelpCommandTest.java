package com.program.commands;

import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HelpCommandTest {

    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void testExecute_PrintsAllCommands() {
        // mock commands
        Command cmd1 = mock(Command.class);
        Command cmd2 = mock(Command.class);

        when(cmd1.getDesc()).thenReturn("desc1");
        when(cmd2.getDesc()).thenReturn("desc2");

        Map<String, Command> commands = Map.of(
                "add", cmd1,
                "del", cmd2
        );

        HelpCommand help = new HelpCommand(commands);

        help.execute("");

        String printed = output.toString();


        assertTrue(printed.contains("Банк не вказаний"));
        assertTrue(printed.contains("====Список всіх команд===="));

        assertTrue(printed.contains("add - desc1"));
        assertTrue(printed.contains("del - desc2"));
    }
}
