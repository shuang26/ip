package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline, a subclass of Task..
 * A Deadline task contains a description, a completion status,
 * and a date and time by which it should be completed.
 */
public class Deadline extends Task {
    protected LocalDateTime deadline;

    /**
     * Constructs a Deadline task with the specified description, completion status, and deadline.
     *
     * @param description The description of the task.
     * @param isDone      The completion status of the task.
     * @param deadline    The deadline of the task.
     */
    public Deadline(String description, boolean isDone, LocalDateTime deadline) {
        super(description, isDone);
        this.deadline = deadline;
    }

    /**
     * Returns a formatted string representation of the task for storage purposes.
     *
     * @return A formatted string representing the task in storage format.
     */
    public String getFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "D | " + super.getFormat() + " | " + deadline.format(formatter);
    }

    /**
     * Returns a string representation of the Deadline task, including its description,
     * completion status, and formatted deadline.
     *
     * @return A string representation of the Deadline task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "[D]" + super.toString() + " (Deadline is: "
                + deadline.format(formatter) + ")";
    }
}
