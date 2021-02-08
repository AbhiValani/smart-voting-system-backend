package com.example.smartvotingsystem.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;

@Document("guest")
public class Guest {

    @Id
    private String guestId;

    @Indexed
    private String roomId;

    @Column(unique = true)
    private String guestName;
    private int guestScore;
    private boolean isVoted;

    public Guest() {
    }

    public Guest(String guestId, String roomId, String guestName, int guestScore, boolean isVoted) {
        this.guestId = guestId;
        this.roomId = roomId;
        this.guestName = guestName;
        this.guestScore = guestScore;
        this.isVoted = isVoted;
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

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getGuestScore() {
        return guestScore;
    }

    public void setGuestScore(int guestScore) {
        this.guestScore = guestScore;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "guestId='" + guestId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", guestName='" + guestName + '\'' +
                ", guestScore=" + guestScore +
                ", isVoted=" + isVoted +
                '}';
    }
}
