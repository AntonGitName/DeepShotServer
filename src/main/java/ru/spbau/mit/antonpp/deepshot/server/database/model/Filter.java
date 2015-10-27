package ru.spbau.mit.antonpp.deepshot.server.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author antonpp
 * @since 26/10/15
 */
@Entity(name = "Filter")
public class Filter {

    @Column(unique = true)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "preview")
    private String preview;

    public Filter() {
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
