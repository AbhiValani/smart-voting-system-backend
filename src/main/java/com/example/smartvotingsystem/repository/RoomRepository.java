package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@EnableMongoRepositories
@Repository
public interface RoomRepository extends MongoRepository<Room , String> , CrudRepository<Room , String> {
}
