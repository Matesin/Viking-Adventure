package tasks;

public class Task {
    public String name;
    public String description;
    private boolean isComplete;
    public int ID;

    public Task(String name, String description, boolean isComplete, int ID) {
        this.name = name;
        this.description = description;
        this.isComplete = isComplete;
        this.ID = ID;
    }

    public boolean getTaskState() {
        return isComplete;
    }
    public void setTaskState(boolean isComplete) {
        this.isComplete = isComplete;
    }
}
