package commands;

import task.TaskList;

/**
 * Represents an unknown command. Upon execution, produces some feedback to the user.
 */
public class UnknownCommand extends Command {
    public final String feedbackToUser;

    public UnknownCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute(TaskList tasks) {
        return new CommandResult(this.feedbackToUser);
    }
}
