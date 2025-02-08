import commands.Command;
import commands.ExitCommand;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import parser.Parser;

import java.util.Scanner;

/**
 * Represents the main class of the Cow application that interacts with the user,
 * manages tasks, and handles storage.
 */

public class Cow {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Cow instance with the specified file path for task storage.
     * Initializes the UI, storage, and loads the tasks from the specified file.
     *
     * @param filePath the path to the file where tasks are saved and loaded from.
     */
    public Cow(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.loadTasksFromFile();
    }

    public static void main(String[] args) {
        new Cow("data/cow.txt").run();
    }

    /**
     * Starts the application, shows the welcome message, and processes user input for task management.
     */
    public void run() {
        ui.showWelcome();
        runUntilExit();
        storage.saveTasksToFile(tasks);
        ui.showExit();
    }

    private void runUntilExit() {
        Scanner sc = new Scanner(System.in);
        Command command;

        while (sc.hasNextLine()) {
            String userInput = sc.nextLine().trim();
            command = new Parser().parseCommand(userInput);

            if (command instanceof ExitCommand) {
                command.execute(this.tasks);
                break;
            }
            command.execute(this.tasks);
        }
        sc.close();
    }
}
