package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.entity.Chat;
import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.repository.ChatRepository;
import com.example.smartvotingsystem.repository.RoomRepository;
import com.example.smartvotingsystem.services.ChatServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;
import rx.schedulers.Schedulers;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServicesImpl implements ChatServices {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    RoomRepository roomRepository;


    @Override
    public Chat addChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public Single<List<Chat>> getChatsByRoomId(String roomId) {
        return Single. <List<Chat>> create(
                singleSubscriber -> {
                    Optional<Room> room = roomRepository.findById(roomId);
                    if (room.isPresent()){
                        List<Chat> chatList = chatRepository.findChatsByRoomId(roomId);
                        singleSubscriber.onSuccess(chatList);
                    }else{
                        singleSubscriber.onError(new EntityNotFoundException());
                    }
                }
        ).subscribeOn(Schedulers.io());
    }
}
