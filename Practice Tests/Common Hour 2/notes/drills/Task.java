/** A task with a priority (bigger number = more urgent). Natural order: most urgent first. */
public class Task implements Comparable<Task> {
    private String name;
    private int priority;

    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    /** Most urgent (highest priority) must come FIRST; ties broken by name A-Z. */
    @Override
    public int compareTo(Task t) {
        // TODO: write this method
        return 0;
    }

    @Override
    public String toString() {
        return name + "(" + priority + ")";
    }
}
