package com.huy.appnoithat.Controller.LuaChonNoiThat.Command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;

public class CommandManager {
    final static Logger LOGGER = LogManager.getLogger(CommandManager.class);

    private final Stack<Command> commandStack = new Stack<>();

    public void push(Command command) {
        commandStack.push(command);
    }

    public void undo() {
        if (commandStack.isEmpty()) {
            return;
        }
        Command command = commandStack.pop();
        command.undo();
    }
}
