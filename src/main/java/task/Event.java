package task;

/**
 * Represents an event task, a subclass of Task.
 * An Event task contains a description, completion status, and start and end dates.
 */
public class Event extends Task {
    protected String fromDate;
    protected String toDate;

    /**
     * Constructs an Event task with the specified description, completion status,
     * start date, and end date.
     *
     * @param description The description of the event.
     * @param isDone      The completion status of the event.
     * @param fromDate    The starting date of the event.
     * @param toDate      The ending date of the event.
     */
    public Event (String description, boolean isDone, String fromDate, String toDate) {
        super(description, isDone);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * Returns a formatted string representation of the event for storage purposes.
     *
     * @return A formatted string representing the event in storage format.
     */
    public String getFormat() {
        return "E | " + super.getFormat() + " | " + fromDate + " | " + toDate;
    }

    /**
     * Returns a string representation of the Event task, including its description,
     * completion status, and date range.
     *
     * @return A string representation of the Event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDate + " to: " + toDate + ")";
    }
}
