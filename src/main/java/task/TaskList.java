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

    /**
     * Adds a task to the task list without returning any output.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Adds a new Todo task to the task list.
     *
     * @param description The description of the task.
     * @param isDone      The completion status of the task.
     * @return A message confirming the addition of the task.
     */
    public String addTodo(String description, boolean isDone) {
        Todo tmp = new Todo(description, isDone);
        addTask(tmp);
        return printAfterAdd(tmp);
    }

    /**
     * Adds a new Deadline task to the task list.
     *
     * @param description The description of the task.
     * @param isDone      The completion status of the task.
     * @param dateTime    The deadline of the task.
     * @return A message confirming the addition of the task.
     */
    public String addDeadline(String description, boolean isDone, LocalDateTime dateTime) {
        Deadline tmp = new Deadline(description, isDone, dateTime);
        addTask(tmp);
        return printAfterAdd(tmp);
    }

    /**
     * Adds a new Event task to the task list.
     *
     * @param description The description of the task.
     * @param isDone      The completion status of the task.
     * @param fromDate    The start date and time of the event.
     * @param toDate      The end date and time of the event.
     * @return A message confirming the addition of the task.
     */
    public String addEvent(String description, boolean isDone,
                           LocalDateTime fromDate, LocalDateTime toDate) {
        Event tmp = new Event(description, isDone, fromDate, toDate);
        addTask(tmp);
        return printAfterAdd(tmp);
    }

    /**
     * Generates a message after adding a task.
     *
     * @param task The task that was added.
     * @return A message confirming the addition of the task.
     */
    private String printAfterAdd(Task task) {
        return "Got it. I've added this task:\n"
                + task + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Lists all tasks in the task list.
     *
     * @return A formatted string of all tasks.
     */
    public String listAllTasks() {
        StringBuilder result = new StringBuilder();

        if (tasks.isEmpty()) {
            result.append("There is no task in your list. Please add some to see the list.");
        } else {
            result.append("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                if (i != 0) {
                    result.append("\n");
                }
                result.append((i + 1) + "." + tasks.get(i));
            }
        }

        return result.toString();
    }

    /**
     * Deletes a task from the task list by its index.
     *
     * @param index The index of the task to be removed (0-based).
     * @return A message confirming the deletion of the task.
     */
    public String deleteTask(int index) {
        Task tmp = tasks.remove(index);
        return "Noted. I've removed this task:\n"
                + tmp + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task to be marked as done (0-based).
     * @return A message confirming the task has been marked.
     */
    public String markTask(int index) {
        Task task = tasks.get(index);
        task.markDone();
        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Unmarks a task (sets it as not done).
     *
     * @param index The index of the task to be unmarked (0-based).
     * @return A message confirming the task has been unmarked.
     */
    public String unmarkTask(int index) {
        Task task = tasks.get(index);
        task.unmarkDone();
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param description The keyword to search for.
     * @return A list of matching tasks or a message if none are found.
     */
    public String findTask(String description) {
        List<String> matches = tasks.stream()
            .filter(t -> t.getDescription().toLowerCase().contains(description.toLowerCase()))
            .map(t -> (tasks.indexOf(t) + 1) + "." + t)
            .toList();

        return matches.isEmpty()
            ? "No matching tasks found."
            : "Here are the matching tasks:\n" + String.join("\n", matches);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return {@code true} if the list is empty, {@code false} otherwise.
     */
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
