package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<String , Chat> {
}
