package com.huy.appnoithat.Controller.LuaChonNoiThat.Command;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;

public class CommandManager {
    private static final int STACK_LIMIT = 20;
    private final Stack<Command> commandStack = new Stack<>();
    @Getter
    private long latestChangeTime = 0;

    private void push(Command command) {
        if (commandStack.size() >= STACK_LIMIT) {
            commandStack.remove(0);
        }
        commandStack.push(command);
        latestChangeTime = System.currentTimeMillis();
    }

    public void execute(Command command) {
        push(command);
        command.execute();
    }

    public void undo() {
        if (commandStack.isEmpty()) {
            return;
        }
        Command command = commandStack.pop();
        command.undo();
    }
}
