package commands;

import java.time.LocalDateTime;

import task.TaskList;

/**
 * Represents the command to add a new task to the task list.
 * It supports the addition of Todo, Deadline, and Event tasks in the TaskList.
 */
public class AddCommand extends Command {
    private String taskType;
    private String taskDescription;
    private boolean isDone;
    private LocalDateTime deadline;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    /**
     * Creates an {@link AddCommand} for adding a Todo task.
     *
     * @param taskType The type of the task (e.g., "todo", "deadline", "event").
     * @param taskDescription A description of the task.
     * @param isDone A boolean indicating whether the task is marked as done.
     */
    public AddCommand(String taskType, String taskDescription, boolean isDone) {
        this.taskType = taskType;
        this.taskDescription = taskDescription;
        this.isDone = isDone;
    }

    /**
     * Creates an {@link AddCommand} for adding a Deadline task.
     *
     * @param taskType The type of the task (e.g., "todo", "deadline", "event").
     * @param taskDescription A description of the task.
     * @param isDone A boolean indicating whether the task is marked as done.
     * @param deadline The deadline date and time for the task.
     */
    public AddCommand(String taskType, String taskDescription, boolean isDone,
                      LocalDateTime deadline) {
        this.taskType = taskType;
        this.taskDescription = taskDescription;
        this.isDone = isDone;
        this.deadline = deadline;
    }

    /**
     * Creates an {@link AddCommand} for adding an Event task.
     *
     * @param taskType The type of the task (e.g., "todo", "deadline", "event").
     * @param taskDescription A description of the task.
     * @param isDone A boolean indicating whether the task is marked as done.
     * @param fromDate The starting date and time for the event.
     * @param toDate The ending date and time for the event.
     */
    public AddCommand(String taskType, String taskDescription, boolean isDone,
                      LocalDateTime fromDate, LocalDateTime toDate) {
        this.taskType = taskType;
        this.taskDescription = taskDescription;
        this.isDone = isDone;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public CommandResult execute(TaskList tasks) {
        if (taskType.equals("todo")) {
            return new CommandResult(tasks.addTodo(taskDescription, isDone));
        } else if (taskType.equals("deadline")) {
            return new CommandResult(tasks.addDeadline(taskDescription, isDone, deadline));
        } else {
            return new CommandResult(tasks.addEvent(taskDescription, isDone, fromDate, toDate));
        }
    }
}
