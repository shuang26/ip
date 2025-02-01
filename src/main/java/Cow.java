import java.util.Scanner;

public class Cow {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Cow(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.loadTasksFromFile();
    }

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
