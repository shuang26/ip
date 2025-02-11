package commands;

import storage.Storage;
import task.TaskList;

/**
 * Represents an executable command.
 */
public class Command {
    protected Command() {
    }

    /**
     * Executes the command on the given task list.
     *
     * @param tasks The task list on which the command operates.
     * @return The result of executing the command.
     * @throws UnsupportedOperationException if the command is not implemented.
     */
    public CommandResult execute(TaskList tasks, Storage storage) {
        throw new UnsupportedOperationException("Not supported yet, only subclasses can call execute.");
    }
    /**
     * Returns the command type.
     * @throws UnsupportedOperationException if the command is not implemented.
     */
    public String getType() {
        throw new UnsupportedOperationException("Not supported yet, only subclasses can call execute.");
    }
}
