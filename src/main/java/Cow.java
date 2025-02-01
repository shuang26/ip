import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cow {
    private Storage storage;
    private TaskList tasks;
    private TaskManager taskManager;
    private Ui ui;

    public Cow(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.loadTasksFromFile();

        // Initialise Task Manager and Load task from saved file / create new file
        this.taskManager = new TaskManager();
        taskManager.start();
    }

    public void run() {
        ui.showWelcome();
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;

        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            if (taskManager.handleInput(input)) {
                break;
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        Cow cow = new Cow("data/cow.txt");
        cow.run();
    }
}
