package commands;

import task.TaskList;

/**
 * Represents the command that lists all tasks in the task list.
 * When executed, it returns the entire list of tasks.
 */
public class ListCommand extends Command {

    @Override
    public CommandResult execute(TaskList tasks) {
        return new CommandResult(tasks.listAllTasks());
    }
}
