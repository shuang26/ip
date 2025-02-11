package commands;

import storage.Storage;
import task.TaskList;

/**
 * Represents the command that exits the application.
 */
public class ExitCommand extends Command {
    private final String commandType = "exit";
    @Override
    public CommandResult execute(TaskList tasks, Storage storage) {
        storage.saveTasksToFile(tasks);
        return new CommandResult("Bye. Hope to see you again soon!");
    }
    @Override
    public String getType() {
        return commandType;
    }
}
