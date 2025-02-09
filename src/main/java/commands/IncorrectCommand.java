package commands;

import task.TaskList;

public class IncorrectCommand extends Command {
    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute(TaskList tasks) {
        return new CommandResult(this.feedbackToUser);
    }
}
