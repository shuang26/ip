package task;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of tasks and provides methods to handle user input.
 */
public class TaskList {
    private final ArrayList<Task> tasks;
    private final String line = "____________________________________________________________\n";

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public String addTodo(String description, boolean isDone) {
        Todo tmp = new Todo(description, isDone);
        return printAfterAdd(tmp);
    }

    public String addDeadline(String description, boolean isDone, LocalDateTime dateTime) {
        Deadline tmp = new Deadline(description, isDone, dateTime);
        return printAfterAdd(tmp);
    }

    public String addEvent(String description, boolean isDone,
                           LocalDateTime fromDate, LocalDateTime toDate) {
        Event tmp = new Event(description, isDone, fromDate, toDate);
        return printAfterAdd(tmp);
    }

    public String printAfterAdd(Task task) {
        return line + "Got it. I've added this task:\n" + task
                + "Now you have " + tasks.size() + " tasks in the list.\n"
                + line + "\n";
    }

    /**
     * Prints out all the tasks in the task list.
     */
    public void listAllTasks() {
        System.out.print(line);

        if (tasks.isEmpty()) {
            System.out.println("There is no task in your list. Please add some to see the list.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }

        System.out.print(line);
    }

    /**
     * Deletes a task from the list by index.
     *
     * @param index The index of the task to delete.
     */
    public void deleteTask(int index) {
        Task tmp = tasks.remove(index);
        System.out.print(line + "Noted. I've removed this task:\n"
                + tmp + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.\n" + line);
    }

    public void markTask(int index) {
        Task task = tasks.get(index);
        task.markDone();
        System.out.print(line + "Nice! I've marked this task as done:\n"
            + task + "\n" + line);
    }

    public void unmarkTask(int index) {
        Task task = tasks.get(index);
        task.unmarkDone();
        System.out.print(line + "OK, I've marked this task as not done yet:\n"
            + task + "\n" + line);
    }

    public void findTask(String description) {
        List<String> matches = tasks.stream()
            .filter(t -> t.getDescription().toLowerCase().contains(description.toLowerCase()))
            .map(t -> (tasks.indexOf(t) + 1) + "." + t)
            .toList();

        printWithLine(matches.isEmpty()
            ? "No matching tasks found."
            : "Here are the matching tasks:\n" + String.join("\n", matches));
    }

    private void printWithLine(String message) {
        System.out.print(line + message + "\n" + line);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns a copy of all tasks in the task list.
     *
     * @return A new {@code ArrayList} containing all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(this.tasks);
    }
}
