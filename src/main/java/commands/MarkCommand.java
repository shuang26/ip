package commands;

import task.TaskList;

public class MarkCommand extends Command {
    private int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(TaskList tasks) {
        if (tasks.isEmpty()) {
            return new CommandResult("Error: Task List is empty. No tasks to mark.");
        }

        if (index < 0 || index >= tasks.size()) {
            return new CommandResult("Error: Index is out of bounds for mark request.");
        }

        return new CommandResult(tasks.markTask(index));
    }
}
