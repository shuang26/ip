package commands;

import task.TaskList;

public class ExitCommand extends Command {

    @Override
    public CommandResult execute(TaskList tasks) {
        return new CommandResult("Bye. Hope to see you again soon!");
    }
}
