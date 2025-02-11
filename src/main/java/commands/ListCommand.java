package commands;

import storage.Storage;
import task.TaskList;

/**
 * Represents the command that lists all tasks in the task list.
 * When executed, it returns the entire list of tasks.
 */
public class ListCommand extends Command {
    private final String commandType = "list";

    @Override
    public CommandResult execute(TaskList tasks, Storage storage) {
        return new CommandResult(tasks.listAllTasks());
    }
    @Override
    public String getType() {
        return commandType;
    }
}
