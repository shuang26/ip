package commands;

import task.Task;
import task.TaskList;

import java.time.LocalDateTime;

public class AddCommand extends Command {
    private String taskType;
    private String taskDescription;
    private boolean isDone;
    private LocalDateTime deadline;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    public AddCommand(String taskType, String taskDescription, boolean isDone) {
        this.taskType = taskType;
        this.taskDescription = taskDescription;
        this.isDone = isDone;
    }

    public AddCommand(String taskType, String taskDescription, boolean isDone,
                      LocalDateTime deadline) {
        this.taskType = taskType;
        this.taskDescription = taskDescription;
        this.isDone = isDone;
        this.deadline = deadline;
    }
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