package com.example.smartvotingsystem.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document("chat")
public class Chat {
    private String roomId;
    private String guestId;
    private String guestName;
    private String chatMessage;

    public Chat() {
    }

    public Chat(String roomId, String guestId, String guestName, String chatMessage) {
        this.roomId = roomId;
        this.guestId = guestId;
        this.guestName = guestName;
        this.chatMessage = chatMessage;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "roomId='" + roomId + '\'' +
                ", guestId='" + guestId + '\'' +
                ", guestName='" + guestName + '\'' +
                ", chatMessage='" + chatMessage + '\'' +
                '}';
    }
}
