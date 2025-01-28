public class Deadline extends Task {
    protected String deadlineDate;

    public Deadline(String description, boolean isDone, String deadlineDate) {
        super(description, isDone);
        this.deadlineDate = deadlineDate;
    }

    public String getFormat() {
        return "D | " + super.getFormat() + " | " + deadlineDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadlineDate + ")";
    }
}
