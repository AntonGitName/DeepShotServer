package ru.spbau.mit.antonpp.deepshot.server;

/**
 * @author antonpp
 * @since 12/10/15
 */
public class Task {

    private final long id;
    private final String content;

    public Task(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}