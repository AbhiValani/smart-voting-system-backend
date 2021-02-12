package com.example.smartvotingsystem.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

import java.util.UUID;

@Document("statementguest")
public class StatementGuest {

    @Id
    private String id = UUID.randomUUID().toString();
    private String statementId;
    private String guestId;
    private int score;

    public StatementGuest() {
    }

    public StatementGuest(String id, String statementId, String guestId, int score) {
        this.id = id;
        this.statementId = statementId;
        this.guestId = guestId;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "StatementGuest{" +
                "id=" + id +
                ", statementId='" + statementId + '\'' +
                ", guestId='" + guestId + '\'' +
                ", score=" + score +
                '}';
    }
}
