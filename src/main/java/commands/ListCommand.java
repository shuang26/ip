package commands;

import task.TaskList;

public class ListCommand extends Command {

    @Override
    public CommandResult execute(TaskList tasks) {
        return new CommandResult(tasks.listAllTasks());
    }
}
