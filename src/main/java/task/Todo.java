package task;

/**
 * Represents a Todo task, a subclass of Task..
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo task with the given description and completion status.
     *
     * @param description The description of the task.
     * @param isDone      The completion status of the task.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns the formatted string representation of the Todo task for storage.
     *
     * @return A formatted string representing the Todo task.
     */
    public String getFormat() {
        return "T | " + super.getFormat();
    }

    /**
     * Returns the string representation of the Todo task.
     *
     * @return A string representing the Todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
