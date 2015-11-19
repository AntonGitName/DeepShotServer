package ru.spbau.mit.antonpp.deepshot.server.controller.response;

/**
 * @author antonpp
 * @since 13/11/15
 */
public class GetStyleResponse {

    private String encodedImage;
    private String name;

    public GetStyleResponse() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }
}
