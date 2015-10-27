package ru.spbau.mit.antonpp.deepshot.server.database.model;

import javax.persistence.*;

/**
 * @author antonpp
 * @since 27/10/15
 */
@Entity(name = "TaskResult")
public class TaskResult {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String encodedImage;

    @ManyToOne
    @JoinColumn(name = "id")
    private TaskRecord taskRecord;

    public TaskResult() {
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

    public TaskRecord getTaskRecord() {
        return taskRecord;
    }

    public void setTaskRecord(TaskRecord taskRecord) {
        this.taskRecord = taskRecord;
    }
}
