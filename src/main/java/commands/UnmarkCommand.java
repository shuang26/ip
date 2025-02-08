package commands;

import task.TaskList;

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
        tasks.unmarkTask(index);
        return new CommandResult("");
    }
}
