import java.util.ArrayList;
import java.util.Scanner;

public class Cow {
    private ArrayList<String> taskList = new ArrayList<>();
    private final String line = "____________________________________________________________\n";

    public static void main(String[] args) {
        Cow cow = new Cow();
        cow.start();
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        System.out.print(line + "Hello! I'm Cow\nWhat can I do for you?\n" + line);

        while (true) {
            String input = sc.nextLine();

            if (input.trim().isEmpty()) {
                continue;
            }


            if (input.equalsIgnoreCase("bye")) { // end chatbot
                System.out.print(line + "Bye. Hope to see you again soon!\n" + line);
                break;

            } else if (input.equalsIgnoreCase("list")) { // list all tasks
                this.listTask();

            } else { // add tasks
                this.addTask(input);
            }
        }
        sc.close();
    }

    public void addTask(String task) {
        taskList.add(task);
        System.out.print(line + "added: " + task + "\n" + line);
    }

    public void listTask() {
        System.out.print(line);
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
        System.out.print(line);
    }
}
