package task;

import parser.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;

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
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks The list of tasks to initialize the TaskList.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Handles user input and executes corresponding commands.
     *
     * @param input The user input command.
     * @return {@code true} if the "bye" command is issued, otherwise {@code false}.
     */
    public boolean handleInput(String input) {
        Parser.ParsedCommand parsed = Parser.parse(input);
        String request = parsed.command;
        String args = parsed.arguments;

        try {
            switch (request) {
            case "bye":
                printWithLine("Bye. Hope to see you again soon!");
                return true;
            case "list":
                this.listAllTask();
                break;
            case "unmark": case "mark":
                if (args.isEmpty()) {
                    printWithLine("Please provide an index for mark/unmark.");
                    break;
                }
                this.handleMarkUnmark(request, args);
                break;
            case "todo": case "deadline": case "event":
                this.addNewTask(request, false, args);
                break;
            case "delete":
                if (args.isEmpty()) {
                    printWithLine("Please provide an index for delete.");
                    break;
                }
                this.deleteTask(args);
                break;
            case "find":
                if (args.isEmpty()) {
                    printWithLine("Cannot search with an empty string");
                }
                this.findTask(args);
                break;
            default:
                printWithLine("Sorry, I don't know what that means.");
            }
        } catch (NumberFormatException e) {
            printWithLine("Please provide a valid index.");
        } catch (ArrayIndexOutOfBoundsException e) {
            printWithLine("Error: Missing information.");
        }
        return false;
    }

    /**
     * Adds a new task to the task list based on the provided type and input.
     *
     * @param taskType The type of task (todo, deadline, event).
     * @param isDone   The completion status of the task.
     * @param input    The user input describing the task.
     */
    public void addNewTask(String taskType, boolean isDone, String input) {
        Task task;

        switch (taskType) {
        case "todo":
            task = createTodo(input, isDone);
            break;
        case "deadline":
            try {
                String[] parts = input.split("/by", 2);
                LocalDateTime dateTime = Parser.parseDateTime(parts[1].trim());
                task = createDeadline(parts[0].trim(), isDone, dateTime);
            } catch (ArrayIndexOutOfBoundsException e) {
                printWithLine("Error: Please provide task description or a deadline for task.Deadline task.");
                return;
            } catch (DateTimeParseException e) {
                printWithLine("Error: Please provide a valid deadline for task.Deadline task.");
                return;
            }
            break;
        case "event":
            // TODO: Possible empty description / empty from date / empty to date
            int fromIndex = input.indexOf("/from");
            int toIndex = input.indexOf("/to");

            if (fromIndex == -1 || toIndex == -1) {
                printWithLine("Error: Please provide /from data or /to date for task.Event task");
                return;
            }
            String description = input.substring(0, fromIndex).trim();
            String fromDate = input.substring(fromIndex + 6, toIndex).trim(); // +6 to skip "/from "
            String toDate = input.substring(toIndex + 4).trim(); // +4 to skip "/to "

            task = createEvent(description, isDone, fromDate, toDate);
            break;
        default:
            printWithLine("Error: Unknown task type. Please provide a valid task type (todo, deadline, event).");
            return;
        }

        // Print after adding task
        System.out.print(line + "Got it. I've added this task:\n");
        System.out.println(task);
        System.out.print("Now you have " + tasks.size() + " tasks in the list.\n" + line);
    }

    /**
     * Creates a new todo task.
     *
     * @param description The description of the task.
     * @param isDone The completion status of the task.
     * @return The created Todo task.
     */
    public Todo createTodo(String description, boolean isDone) {
        Todo tmp = new Todo(description, isDone);
        tasks.add(tmp);
        return tmp;
    }

    /**
     * Adds a new deadline task to the list.
     *
     * @param description The description of the task.
     * @param isDone The completion status of the task.
     * @param dateTime The deadline of the task.
     * @return The created Deadline task.
     */
    public Deadline createDeadline(String description, boolean isDone, LocalDateTime dateTime) {
        Deadline tmp = new Deadline(description, isDone, dateTime);
        tasks.add(tmp);
        return tmp;
    }

    /**
     * Create a new event task.
     *
     * @param description The description of the task.
     * @param isDone The completion status of the task.
     * @param fromDate The start date of the event.
     * @param toDate The end date of the event.
     * @return The created Event task.
     */
    public Event createEvent(String description, boolean isDone, String fromDate, String toDate) {
        Event tmp = new Event(description, isDone, fromDate, toDate);
        tasks.add(tmp);
        return tmp;
    }

    /**
     * Prints out all the tasks in the task list.
     */
    public void listAllTask() {
        System.out.print(line);
        if (tasks.isEmpty()) {
            System.out.println("There is no task in your list. Please add some to see the list.");
            System.out.println(line);
            return;
        }

        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            Task tmp = tasks.get(i);
            System.out.println((i + 1) + "." + tmp);
        }
        System.out.print(line);
    }

    /**
     * Deletes a task from the list by index.
     *
     * @param index The index of the task to delete.
     */
    public void deleteTask(String index) {
        int i = Integer.parseInt(index) - 1;

        if (tasks.isEmpty()) {
            printWithLine("Error: Cannot delete from an empty list.");
            return;
        } else if (i < 0 || i >= tasks.size()) {
            printWithLine("Error: Index must be between 1 and " + tasks.size() + " for delete request.");
            return;
        }

        System.out.print(line + "Noted. I've removed this task:\n");
        Task tmp = tasks.remove(i);
        System.out.print(tmp + "\n");
        System.out.print("Now you have " + tasks.size() + " tasks in the list.\n" + line);
    }

    /**
     * Handles marking or unmarking a task based on the provided request and index.
     *
     * @param request The action to perform, either "mark" or "unmark".
     * @param index   The index of the task to be marked or unmarked (1-based index).
     * @throws NumberFormatException If the provided index is not a valid number.
     */
    public void handleMarkUnmark(String request, String index) throws NumberFormatException {
        int i = Integer.parseInt(index) - 1;

        if (tasks.isEmpty()) {
            printWithLine("Error: Cannot " + request + " an empty list.");
            return;
        } else if (i < 0 || i >= tasks.size()) {
            printWithLine("Error: index is out of bounds for " + request + " request.");
            return;
        }

        if (request.equals("mark")) this.markTask(i);
        else this.unmarkTask(i);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index The index of the task to mark as done (0-based index).
     */
    private void markTask(int index) {
        System.out.print(line + "Nice! I've marked this task as done:\n");
        Task tmp = tasks.get(index);
        tmp.markDone();
        System.out.print(tmp + "\n" + line);
    }

    /**
     * Unmarks the task at the specified index as done.
     *
     * @param index The index of the task to mark as done (0-based index).
     */
    private void unmarkTask(int index) {
        System.out.print(line + "OK, I've marked this task as not done yet:\n");
        Task tmp = tasks.get(index);
        tmp.unmarkDone();
        System.out.print(tmp + "\n" + line);
    }

    public void findTask(String description) {
        if (tasks.isEmpty()) {
            printWithLine("TaskList is empty");
            return;
        }

        ArrayList<String> tmp = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);

            if (t.getDescription().toLowerCase().contains(description.toLowerCase())) {
                tmp.add((i + 1) + "." + t.toString());
            }
        }
        if (tmp.isEmpty()) {
            printWithLine("No matching task is found in the Task List.");
        } else {
            System.out.print(line + "Here are the matching tasks in your list:\n");
            for (String s : tmp) {
                System.out.println(s);
            }
            System.out.print(line);
        }
    }

    private void printWithLine(String message) {
        System.out.print(line + message + "\n" + line);
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return {@code true} if the task list is empty, {@code false} otherwise.
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
