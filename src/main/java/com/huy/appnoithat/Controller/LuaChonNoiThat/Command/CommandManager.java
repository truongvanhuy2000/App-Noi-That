package com.huy.appnoithat.Controller.LuaChonNoiThat.Command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;

public class CommandManager {
    final Logger LOGGER = LogManager.getLogger(this);
    private static final int STACK_LIMIT = 20;
    private final Stack<Command> commandStack = new Stack<>();

    public void push(Command command) {
        if (commandStack.size() >= STACK_LIMIT) {
            commandStack.remove(0);
        }
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
