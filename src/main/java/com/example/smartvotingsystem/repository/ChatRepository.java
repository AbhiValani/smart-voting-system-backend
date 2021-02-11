package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Chat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    @Query(nativeQuery = true, value = "select * from chat c where c.room_id=?1")
    List<Chat> findChatsByRoomId(String roomId);
}
