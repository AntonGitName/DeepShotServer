package ru.spbau.mit.antonpp.deepshot.server.database.model;

import javax.persistence.*;

/**
 * @author antonpp
 * @since 27/10/15
 */
@Entity(name = "MLOutputRecord")
public class MLOutputRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String imageUrl;

    @Column
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id")
    private Long userInputRecord;

    public MLOutputRecord() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public Long getUserInputRecord() {
        return userInputRecord;
    }

    public void setUserInputRecord(Long userInputRecord) {
        this.userInputRecord = userInputRecord;
    }

    public static enum Status {
        READY, PROCESSING, FAILED
    }


}
