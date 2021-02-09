package com.example.smartvotingsystem.dto;

public class RoomGuest {
    private String roomName;
    private String guestName;
    private String roomId;
    public RoomGuest() {
    }

    public RoomGuest(String roomName, String guestName, String roomId) {
        this.roomName = roomName;
        this.guestName = guestName;
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "RoomGuest{" +
                "roomName='" + roomName + '\'' +
                ", guestName='" + guestName + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
