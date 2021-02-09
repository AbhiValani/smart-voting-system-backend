package com.example.smartvotingsystem.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Document("statement")
public class Statement {
    @Id
    private String statementId = UUID.randomUUID().toString();

    @Indexed
    private String roomId;
    private String statementText;
    private double avgScore;
    private double medianScore;

    public Statement() {
    }

    public Statement(String statementId, String roomId, String statementText, double avgScore, double medianScore) {
        this.statementId = statementId;
        this.roomId = roomId;
        this.statementText = statementText;
        this.avgScore = avgScore;
        this.medianScore = medianScore;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStatementText() {
        return statementText;
    }

    public void setStatementText(String statementText) {
        this.statementText = statementText;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public double getMedianScore() {
        return medianScore;
    }

    public void setMedianScore(double medianScore) {
        this.medianScore = medianScore;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "statementId='" + statementId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", statementText='" + statementText + '\'' +
                ", avgScore=" + avgScore +
                ", medianScore=" + medianScore +
                '}';
    }
}
