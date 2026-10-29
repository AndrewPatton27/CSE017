/**
 * A printing request. Natural ordering is by user group:
 * root (highest priority) < admin < user < batch (lowest priority).
 */
public class PrintRequest implements Comparable<PrintRequest> {
    private static final String[] GROUPS = {"root", "admin", "user", "batch"};

    private int userId;
    private String group;
    private long size; // bytes

    public PrintRequest(int userId, String group, long size) {
        this.userId = userId;
        setGroup(group);
        this.size = size;
    }

    public int getUserId() { return userId; }
    public String getGroup() { return group; }
    public long getSize() { return size; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setSize(long size) { this.size = size; }
    public void setGroup(String group) {
        if (priority(group) == -1) {
            throw new IllegalArgumentException("Invalid group: " + group);
        }
        this.group = group.toLowerCase();
    }

    /** Lower number = higher priority. */
    private static int priority(String group) {
        for (int i = 0; i < GROUPS.length; i++) {
            if (GROUPS[i].equalsIgnoreCase(group)) return i;
        }
        return -1;
    }

    @Override
    public int compareTo(PrintRequest other) {
        return Integer.compare(priority(group), priority(other.group));
    }

    /** Formats a byte count as B, KB, MB, or GB with one decimal. */
    public static String formatSize(long bytes) {
        if (bytes < 1_000) return bytes + "B";
        if (bytes < 1_000_000) return String.format("%.1fKB", bytes / 1e3);
        if (bytes < 1_000_000_000) return String.format("%.1fMB", bytes / 1e6);
        return String.format("%.1fGB", bytes / 1e9);
    }

    @Override
    public String toString() {
        return String.format("%-12d%-12s%-12s", userId, group, formatSize(size));
    }
}
