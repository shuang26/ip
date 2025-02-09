package commands;

import task.TaskList;

/**
 * Represents the command that is executed when the user enters an incorrect command.
 * This command simply provides feedback to the user, indicating that the command is invalid.
 */
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
