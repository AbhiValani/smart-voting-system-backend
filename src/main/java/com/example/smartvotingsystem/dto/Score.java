package com.example.smartvotingsystem.dto;

public class Score {
    private String guestId;
    private String roomId;
    private String statementId;
    private int score;

    public Score() {
    }

    public Score(String guestId, String roomId, String statementId, int score) {
        this.guestId = guestId;
        this.roomId = roomId;
        this.statementId = statementId;
        this.score = score;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "guestId='" + guestId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", statementId='" + statementId + '\'' +
                ", score=" + score +
                '}';
    }
}
