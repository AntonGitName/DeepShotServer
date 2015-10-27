package ru.spbau.mit.antonpp.deepshot.server.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author antonpp
 * @since 26/10/15
 */
@Entity(name = "TaskRecord")
public class TaskRecord {

    @Column(unique = true)
    private Long id;

    @Column
    private String encodedImage;

    @ManyToOne
    @JoinColumn(name = "id")
    private Filter filter;

    public TaskRecord() {
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }
}
