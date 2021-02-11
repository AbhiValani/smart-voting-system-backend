package com.example.smartvotingsystem.services;


import com.example.smartvotingsystem.dto.BaseWebResponse;
import com.example.smartvotingsystem.entity.Chat;
import org.springframework.stereotype.Service;
import rx.Single;

import java.util.List;

@Service
public interface ChatServices {
    //Single<Chat> addChat(Chat chat);
    Chat addChat(Chat chat);
    Single<List<Chat>> getChatsByRoomId(String roomId);
}
