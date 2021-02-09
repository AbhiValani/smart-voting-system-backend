package com.example.smartvotingsystem.dto;

public class RoomAdmin {
    private String roomId;
    private String roomName;

    public RoomAdmin() {
    }

    public RoomAdmin(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "RoomAdmin{" +
                "roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
