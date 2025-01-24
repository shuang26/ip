import java.util.ArrayList;
import java.util.Scanner;

public class Cow {
    private ArrayList<Task> taskList = new ArrayList<>();
    private final String line = "____________________________________________________________\n";

    public static void main(String[] args) {
        Cow cow = new Cow();
        cow.start();
    }

    public void start() {
        System.out.print(line + "Hello! I'm Cow\nWhat can I do for you?\n" + line);
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            if (input.trim().isEmpty()) continue;

            String[] parts = input.split(" ");
            String action = parts[0].toLowerCase();

            if (action.equals("bye")) {
                System.out.print(line + "Bye. Hope to see you again soon!\n" + line);
                break;
            } else if (action.equals("list")) { // list all tasks
                this.listTask();
            } else if (action.equals("mark")) {
                int index = Integer.parseInt(parts[1]) - 1;
                if (this.checkIndex(index)) this.markTask(index);
                else continue;
            } else if (action.equals("unmark")) {
                int index = Integer.parseInt(parts[1]) - 1;
                // Check if index is a valid value
                if (this.checkIndex(index)) this.unmarkTask(index);
                else continue;

            } else {
                this.addTask(input);
            }
        }
        sc.close();
    }

    public void addTask(String description) {
        Task tmp = new Task(description);
        taskList.add(tmp);
        System.out.print(line + tmp + "\n" + line);
    }

    public void listTask() {
        System.out.print(line);
        System.out.println("Here are the tasks in your list:\n");

        for (int i = 0; i < taskList.size(); i++) {
            Task tmp = taskList.get(i);
            System.out.println((i + 1) + "." + tmp);
        }
        System.out.print(line);
    }

    public void markTask(int index) {
        System.out.print(line + "Nice! I've marked this task as done:\n");
        Task tmp = taskList.get(index);
        tmp.isDone = true;
        System.out.print(tmp + "\n" + line);
    }

    public void unmarkTask(int index) {
        System.out.print(line + "OK, I've marked this task as not done yet:\n");
        Task tmp = taskList.get(index);
        tmp.isDone = false;
        System.out.print(tmp + "\n" + line);
    }

    private boolean checkIndex(int index) {
        if (index < 0 || index >= taskList.size()) return false;
        return true;
    }
}
