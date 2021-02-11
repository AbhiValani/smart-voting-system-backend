package com.example.smartvotingsystem.dto;

public class RoomPassword {
    private String roomId;
    private String password;

    public RoomPassword(String roomId, String password) {
        this.roomId = roomId;
        this.password = password;
    }

    public RoomPassword() {
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RoomPassword{" +
                "roomId='" + roomId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
