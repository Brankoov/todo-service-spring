package se.brankoov.todoservice.statistics;

public class TodoStats {
    private String title;
    private long count;

    public TodoStats(String title, long count) {
        this.title = title;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public long getCount() {
        return count;
    }
}
