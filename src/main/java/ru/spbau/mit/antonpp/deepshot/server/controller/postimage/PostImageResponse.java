package ru.spbau.mit.antonpp.deepshot.server.controller.postimage;

/**
 * @author antonpp
 * @since 12/10/15
 */
public class PostImageResponse {

    private final long id;

    public PostImageResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}