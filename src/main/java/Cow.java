import java.util.ArrayList;
import java.util.Scanner;

public class Cow {
    private final ArrayList<Task> taskList = new ArrayList<>();
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

            String[] parts = input.split(" ", 2);
            String request = parts[0].toLowerCase();

            try {
                switch (request) {
                    case "bye":
                        System.out.print(line + "Bye. Hope to see you again soon!\n" + line);
                        return;
                    case "list":
                        this.listTask(); // List can even if there is extra words behind
                        break;
                    case "mark":
                        this.markTask(Integer.parseInt(parts[1]) - 1);
                        break;
                    case "unmark":
                        this.unmarkTask(Integer.parseInt(parts[1]) - 1);
                        break;
                    case "todo":
                        if (parts.length == 2) {
                            this.addTodoTask(parts[1]);
                        } else {
                            System.out.print(line + "Missing description for Todo task\n" + line);
                        }
                        break;
                    case "deadline":
                        if (parts.length == 2) {
                            this.addDeadlineTask(parts[1]);
                        } else {
                            System.out.print(line + "Missing description / deadline for Deadline task\n" + line);
                        }
                        break;
                    case "event":
                        if (parts.length == 2) {
                            this.addEventTask(parts[1]);
                        } else {
                            System.out.print(line + "Missing description / from / to for Event task\n" + line);
                        }
                        break;
                    default:
                        System.out.print(line + "Sorry, but I don't know what that means.\nPlease try again.\n" + line);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format. Please provide a valid index."); // For mark and unmark
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Index out of bounds. Please provide a valid task number.");
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Error: Please provide a valid /from and /to for Event task.");
            }
        }
        sc.close();
    }

    public void addTodoTask(String input) {
        Todo tmp = new Todo(input);
        taskList.add(tmp);
        this.printAfterAdding(tmp);
    }

    public void addDeadlineTask(String input) {
        String[] parts = input.split("/by", 2);
        if (parts.length != 2) {
            System.out.println("/by is not found for Deadline task");
            return;
        }

        Deadline tmp = new Deadline(parts[0].trim(), parts[1].trim());
        taskList.add(tmp);
        this.printAfterAdding(tmp);
    }

    public void addEventTask(String input) {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == 1) {
            System.out.println("/from or / to is not found for Event task");
            return;
        }

        String description = input.substring(0, fromIndex).trim();
        String from = input.substring(fromIndex + 6, toIndex).trim(); // +6 to skip "/from "
        String to = input.substring(toIndex + 4).trim(); // +4 to skip "/to "

        Event tmp = new Event(description, from, to);
        taskList.add(tmp);
        this.printAfterAdding(tmp);
    }

    public void printAfterAdding(Task task) {
        System.out.print(line + "Got it. I've added this task:\n");
        System.out.println(task);
        System.out.print("Now you have " + taskList.size() + " tasks in the list.\n" + line);
    }

    public void listTask() {
        System.out.print(line);
        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < taskList.size(); i++) {
            Task tmp = taskList.get(i);
            System.out.println((i + 1) + "." + tmp);
        }
        System.out.print(line);
    }

    public void markTask(int index) {
        if (this.checkIndex(index)) {
            System.out.print(line + "Nice! I've marked this task as done:\n");
            Task tmp = taskList.get(index);
            tmp.markDone();
            System.out.print(tmp + "\n" + line);
        } else {
            System.out.println("Error: index is out of bounds");
        }
    }

    public void unmarkTask(int index) {
        if (this.checkIndex(index)) {
            System.out.print(line + "OK, I've marked this task as not done yet:\n");
            Task tmp = taskList.get(index);
            tmp.unmarkDone();
            System.out.print(tmp + "\n" + line);
        } else {
            System.out.println("Error: index is out of bounds");
        }
    }

    private boolean checkIndex(int index) {
        if (index < 0 || index >= taskList.size()) return false;
        return true;
    }
}
