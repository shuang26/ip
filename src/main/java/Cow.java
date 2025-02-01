import java.util.Scanner;

public class Cow {
    private TaskManager taskManager;
    private Ui ui;

    public Cow() {
        this.ui = new Ui();
    }

    public void start() {
        // Initialise Task Manager and Load task from saved file / create new file
        this.taskManager = new TaskManager();
        taskManager.start();

        ui.showWelcome();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();

            if (input.isEmpty()) continue;
            if (taskManager.handleInput(input)) break;
        }
        sc.close();
    }

    public static void main(String[] args) {
        Cow cow = new Cow();
        cow.start();
    }
}
