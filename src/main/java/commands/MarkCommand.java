package commands;

import storage.Storage;
import task.TaskList;

/**
 * Represents the command that marks a task as completed in the task list.
 */
public class MarkCommand extends Command {
    private final String commandType = "mark";
    private int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(TaskList tasks, Storage storage) {
        if (tasks.isEmpty()) {
            return new CommandResult("Error: Task List is empty. No tasks to mark.");
        }

        if (index < 0 || index >= tasks.size()) {
            return new CommandResult("Error: Index is out of bounds for mark request.");
        }

        return new CommandResult(tasks.markTask(index));
    }
    @Override
    public String getType() {
        return commandType;
    }
}
