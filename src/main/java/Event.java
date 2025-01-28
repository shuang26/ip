public class Event extends Task {
    protected String fromDate;
    protected String toDate;

    public Event (String description, boolean isDone, String fromDate, String toDate) {
        super(description, isDone);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getFormat() {
        return "E | " + super.getFormat() + " | " + fromDate + " | " + toDate;
    }


    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDate + " to: " + toDate + ")";
    }
}
