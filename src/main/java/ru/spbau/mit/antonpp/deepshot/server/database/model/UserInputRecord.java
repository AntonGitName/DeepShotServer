package ru.spbau.mit.antonpp.deepshot.server.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author antonpp
 * @since 26/10/15
 */
@Entity(name = "UserInputRecord")
public class UserInputRecord {

    @Column(unique = true)
    private Long id;

    @Column
    private String username;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "id")
    private Style style;

    public UserInputRecord() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
