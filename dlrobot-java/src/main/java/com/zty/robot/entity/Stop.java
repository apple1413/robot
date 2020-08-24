package com.zty.robot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stop {

    @Id
    private String id;

    @Column
    private String stopword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStopword() {
        return stopword;
    }

    public void setStopword(String stopword) {
        this.stopword = stopword;
    }
}
