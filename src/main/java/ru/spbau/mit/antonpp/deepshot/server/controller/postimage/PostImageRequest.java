package ru.spbau.mit.antonpp.deepshot.server.controller.postimage;

/**
 * @author antonpp
 * @since 27/10/15
 */
public class PostImageRequest {
    private String encodedImage;
    private Long filterId;

    public PostImageRequest() {
    }

    public String getEncodedImage() {

        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public Long getFilterId() {
        return filterId;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }
}
