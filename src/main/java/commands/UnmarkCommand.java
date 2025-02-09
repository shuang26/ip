package commands;

import task.TaskList;

/**
 * Represents the command that unmarks a task as completed in the task list.
 */
public class UnmarkCommand extends Command {
    private int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(TaskList tasks) {
        if (tasks.isEmpty()) {
            return new CommandResult("Error: Task List is empty. No tasks to unmark.");
        }

        if (index < 0 || index >= tasks.size()) {
            return new CommandResult("Error: index is out of bounds for unmark request.");
        }

        return new CommandResult(tasks.unmarkTask(index));
    }
}
