package commands;

import storage.Storage;
import task.TaskList;

/**
 * Represents an unknown command. Upon execution, produces some feedback to the user.
 */
public class UnknownCommand extends Command {
    private final String commandType = "unknown";
    private final String feedbackToUser;

    public UnknownCommand(String feedbackToUser) {
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
