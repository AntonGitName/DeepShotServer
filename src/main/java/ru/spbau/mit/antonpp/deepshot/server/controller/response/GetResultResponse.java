package ru.spbau.mit.antonpp.deepshot.server.controller.response;

import ru.spbau.mit.antonpp.deepshot.server.database.model.MLOutputRecord;

/**
 * @author antonpp
 * @since 13/11/15
 */
public class GetResultResponse {
    private MLOutputRecord.Status status;
    private String encodedImage;

    public GetResultResponse() {
    }

    public MLOutputRecord.Status getStatus() {
        return status;
    }

    public void setStatus(MLOutputRecord.Status status) {
        this.status = status;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }
}
