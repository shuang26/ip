import java.util.Scanner;

public class Cow {
    private TaskManager taskManager;

    public static void main(String[] args) {
        Cow cow = new Cow();
        cow.start();
    }

    public void start() {
        // Initialise Task Manager and Load task from saved file / create new file
        this.taskManager = new TaskManager();
        taskManager.start();

        printStart();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();

            if (input.isEmpty()) continue;
            if (taskManager.handleInput(input)) break;
        }
        sc.close();
    }

    public void printStart() {
        String line = "____________________________________________________________\n";
        System.out.print(line + "Hello! I'm Cow\nWhat can I do for you?\n" + line);
    }
}
