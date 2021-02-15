package com.example.smartvotingsystem.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("room")
public class Room {
    @Id
    private String roomId = UUID.randomUUID().toString();

    @Indexed(unique = true)
    private String roomName;
    private String roomDescription;
    private String password;

    public Room() {
    }

    public Room(String roomName, String roomDescription, String password) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.password = password;
    }

    public Room(String roomId, String roomName, String roomDescription, String password) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.password = password;
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

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomDescription='" + roomDescription + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
