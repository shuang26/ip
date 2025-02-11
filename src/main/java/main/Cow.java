package main;

import commands.Command;
import commands.CommandResult;
import parser.Parser;
import storage.Storage;
import task.TaskList;

/**
 * Represents the main class of the cow.Cow application that interacts with the user,
 * manages tasks, and handles storage.
 */
public class Cow {
    private final Storage storage;
    private final TaskList tasks;
    private String commandType;

    /**
     * Constructs a new cow.Cow instance with the specified file path for task storage.
     * Initializes the UI, storage, and loads the tasks from the specified file.
     *
     * @param filePath the path to the file where tasks are saved and loaded from.
     */
    public Cow(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = storage.loadTasksFromFile();
    }

    public String getResponse(String input) {
        Command command = new Parser().parseCommand(input.trim());
        CommandResult output = command.execute(tasks, storage);
        this.commandType = command.getType();

        return output.toString();
    }

    public String getCommandType() {
        return commandType;
    }
}
