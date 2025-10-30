package com.program.commands;

import java.io.Console;

public interface Command {

    String getDesc();
    void execute(String param);
}
