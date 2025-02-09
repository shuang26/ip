package commands;

import task.TaskList;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(TaskList tasks) {
        if (tasks.isEmpty()) {
            return new CommandResult("Error: Task List is empty. No tasks to mark.");
        }

        if (index < 0 || index >= tasks.size()) {
            return new CommandResult("Error: Index is out of bounds for delete request.");
        }

        return new CommandResult(tasks.deleteTask(index));
    }
}
