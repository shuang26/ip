package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task, a subclass of Task.
 * An Event task contains a description, completion status, and start and end dates.
 */
public class Event extends Task {
    protected LocalDateTime fromDate;
    protected LocalDateTime toDate;

    /**
     * Constructs an Event task with the specified description, completion status,
     * start date, and end date.
     *
     * @param description The description of the event.
     * @param isDone      The completion status of the event.
     * @param fromDate    The starting date of the event.
     * @param toDate      The ending date of the event.
     */
    public Event (String description, boolean isDone, LocalDateTime fromDate, LocalDateTime toDate) {
        super(description, isDone);
        this.fromDate = fromDate;
        this.toDate = toDate;
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
     * Returns a formatted string representation of the event for storage purposes.
     *
     * @return A formatted string representing the event in storage format.
     */
    public String getFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return "E | " + super.getFormat() + " | " + fromDate.format(formatter)
            + " | " + toDate.format(formatter);
    }

    /**
     * Returns a string representation of the Event task, including its description,
     * completion status, and date range.
     *
     * @return A string representation of the Event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatDateTime(fromDate)
            + " to: " + formatDateTime(toDate) + ")";
    }
}
