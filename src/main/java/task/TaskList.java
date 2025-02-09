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
        addTask(tmp);
        return printAfterAdd(tmp);
    }

    public String addDeadline(String description, boolean isDone, LocalDateTime dateTime) {
        Deadline tmp = new Deadline(description, isDone, dateTime);
        addTask(tmp);
        return printAfterAdd(tmp);
    }

    public String addEvent(String description, boolean isDone,
                           LocalDateTime fromDate, LocalDateTime toDate) {
        Event tmp = new Event(description, isDone, fromDate, toDate);
        addTask(tmp);
        return printAfterAdd(tmp);
    }

    public String printAfterAdd(Task task) {
        return "Got it. I've added this task:\n"
                + task + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
    }

    public String listAllTasks() {
        StringBuilder result = new StringBuilder();

        if (tasks.isEmpty()) {
            result.append("There is no task in your list. Please add some to see the list.");
        } else {
            result.append("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                result.append((i + 1) + "." + tasks.get(i) + "\n");
            }
        }

        return result.toString();
    }


    public String deleteTask(int index) {
        Task tmp = tasks.remove(index);
        return "Noted. I've removed this task:\n"
                + tmp + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
    }

    public String markTask(int index) {
        Task task = tasks.get(index);
        task.markDone();
        return "Nice! I've marked this task as done:\n" + task;
    }

    public String unmarkTask(int index) {
        Task task = tasks.get(index);
        task.unmarkDone();
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    public String findTask(String description) {
        List<String> matches = tasks.stream()
            .filter(t -> t.getDescription().toLowerCase().contains(description.toLowerCase()))
            .map(t -> (tasks.indexOf(t) + 1) + "." + t)
            .toList();

        return matches.isEmpty()
            ? "No matching tasks found."
            : "Here are the matching tasks:\n" + String.join("\n", matches);
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
