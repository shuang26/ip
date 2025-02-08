package commands;

import task.TaskList;

/**
 * Represents an executable command.
 */
public class Command {
    protected Command() {
    }

    public CommandResult execute(TaskList tasks) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
