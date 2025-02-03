import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.util.Scanner;

/**
 * Represents the main class of the Cow application that interacts with the user,
 * manages tasks, and handles storage.
 */

public class Cow {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

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

    /**
     * Starts the application, shows the welcome message, and processes user input for task management.
     */
    public void run() {
        ui.showWelcome();
        Scanner sc = new Scanner(System.in);

        // boolean isExit = false;

        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            if (tasks.handleInput(input)) {
                break;
            }
        }

        storage.saveTasksToFile(tasks);
        sc.close();
    }

    public static void main(String[] args) {
        new Cow("data/cow.txt").run();
    }
}
