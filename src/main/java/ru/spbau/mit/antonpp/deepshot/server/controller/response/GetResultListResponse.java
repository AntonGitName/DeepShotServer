package ru.spbau.mit.antonpp.deepshot.server.controller.response;

import java.util.List;

/**
 * @author antonpp
 * @since 13/11/15
 */
public class GetResultListResponse {
    private List<Long> ids;

    public GetResultListResponse() {
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
