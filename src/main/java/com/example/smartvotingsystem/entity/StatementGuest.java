package com.example.smartvotingsystem.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;
@Document("statementguest")
public class StatementGuest {

    @Id
    private String statementId;
    private String guestId;
    private int score;

    public StatementGuest() {
    }

    public StatementGuest(String statementId, String guestId, int score) {
        this.statementId = statementId;
        this.guestId = guestId;
        this.score = score;
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
                "statementId='" + statementId + '\'' +
                ", guestId='" + guestId + '\'' +
                ", score=" + score +
                '}';
    }
}
