package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<String , Room> {
}
