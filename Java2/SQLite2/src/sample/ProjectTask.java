package sample;

public class ProjectTask {

    private final String description;

    private final long startDate;
    private final long length;
    private boolean isDone = false;

    public ProjectTask(String description, long startDate, long length) {
        this.description = description;
        this.startDate = startDate;
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void taskDone() {
        isDone = true;
    }

    public boolean isExpired() {
        return (startDate + length) > System.currentTimeMillis();
    }

    public String getStartDate() {
        return Long.toString(startDate);
    }

    public String getLength() {
        return Long.toString(length);
    }
}
