package task;

public class Todo extends Task {
    public Todo (String description, boolean isDone) {
        super(description, isDone);
    }

    public String getFormat() {
        return "T | " + super.getFormat();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
