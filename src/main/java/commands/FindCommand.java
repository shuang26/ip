package commands;

import task.TaskList;

public class FindCommand extends Command {
    private String description;

    public FindCommand (String description) {
        this.description = description;
    }

    @Override
    public CommandResult execute(TaskList tasks) {
        if (description.isEmpty()) {
            return new CommandResult("Error: Cannot find an empty description.");
        }
        tasks.findTask(description);

        return new CommandResult("");
    }
}
