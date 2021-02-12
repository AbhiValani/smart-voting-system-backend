package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Guest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends MongoRepository<Guest , String> , CrudRepository<Guest , String> {

    List<Guest> findByRoomId(String roomId);
}
