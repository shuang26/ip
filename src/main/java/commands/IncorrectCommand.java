package commands;

import storage.Storage;
import task.TaskList;

/**
 * Represents the command that is executed when the user enters an incorrect command.
 * This command simply provides feedback to the user, indicating that the command is invalid.
 */
public class IncorrectCommand extends Command {
    private final String commandType = "incorrect";
    private final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute(TaskList tasks, Storage storage) {
        return new CommandResult(this.feedbackToUser);
    }
    @Override
    public String getType() {
        return commandType;
    }
}
