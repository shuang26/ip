package task;

/**
 * Represents a task with a description and a completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the specified description and completion status.
     *
     * @param description The description of the task.
     * @param isDone      The completion status of the task.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "[X]" if the task is done, "[ ]" otherwise.
     */
    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]"; // mark done task with X
    }

    /**
     * Returns a copy of the task's description.
     *
     * @return A copy of the description of the task.
     */
    public String getDescription() {
        return new String(description);
    }


    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmarkDone() {
        this.isDone = false;
    }

    /**
     * Checks if the task is done.
     *
     * @return {@code true} if the task is done, {@code false} otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns a formatted string representation of the task for storage.
     *
     * @return A string in the format "1 | description" if done, "0 | description" otherwise.
     */
    public String getFormat() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A string containing the status icon and the description.
     */
    @Override
    public String toString() {
        return this.getStatusIcon() + " " + this.description;
    }
}
