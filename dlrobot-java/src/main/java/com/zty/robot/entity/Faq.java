package com.zty.robot.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Faq {

    @Id
    private String fid;

    @Column
    private String question;

    @Column
    private String answers;

    private Double indexNum;

    public Double getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Double indexNum) {
        this.indexNum = indexNum;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }


}
