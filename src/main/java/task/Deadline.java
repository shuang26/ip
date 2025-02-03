package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline, a subclass of Task..
 * A Deadline task contains a description, a completion status,
 * and a date and time by which it should be completed.
 */
public class Deadline extends Task {
    protected LocalDateTime dateTime;

    /**
     * Constructs a Deadline task with the specified description, completion status, and deadline.
     *
     * @param description The description of the task.
     * @param isDone      The completion status of the task.
     * @param dateTime    The deadline of the task.
     */
    public Deadline(String description, boolean isDone, LocalDateTime dateTime) {
        super(description, isDone);
        this.dateTime = dateTime;
    }

    /**
     * Formats the given LocalDateTime into a human-readable string format.
     *
     * @param dateTime The LocalDateTime to format.
     * @return A formatted string representing the date and time.
     */
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, h:mma");
        return dateTime.format(formatter);
    }

    /**
     * Returns a formatted string representation of the task for storage purposes.
     *
     * @return A formatted string representing the task in storage format.
     */
    public String getFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return "D | " + super.getFormat() + " | " + dateTime.format(formatter);
    }

    /**
     * Returns a string representation of the Deadline task, including its description,
     * completion status, and formatted deadline.
     *
     * @return A string representation of the Deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatDateTime(dateTime) + ")";
    }
}
