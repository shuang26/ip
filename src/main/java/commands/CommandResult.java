package commands;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    public final String commandOutput;

    public CommandResult(String commandOutput) {
        this.commandOutput = commandOutput;
    }

    @Override
    public String toString() {
        String line = "____________________________________________________________\n";
        return line + commandOutput + "\n" + line;
    }
}
