package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findChatsByRoomId(String roomId);

    void deleteByRoomId(String roomId);
}
