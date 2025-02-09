package commands;

import task.TaskList;

/**
 * Represents the command that searches for tasks with a description matching the given query.
 * When executed, it returns all the tasks that match the description.
 */
public class FindCommand extends Command {
    private String description;

    public FindCommand(String description) {
        this.description = description;
    }

    @Override
    public CommandResult execute(TaskList tasks) {
        if (description.isEmpty()) {
            return new CommandResult("Error: Cannot find an empty description.");
        }

        return new CommandResult(tasks.findTask(description));
    }
}
