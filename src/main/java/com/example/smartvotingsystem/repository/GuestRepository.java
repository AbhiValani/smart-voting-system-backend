package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Guest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends MongoRepository<Guest , String> , CrudRepository<Guest , String> {

    @Query(nativeQuery = true, value = "select * from guest g where g.room_id=?1")
    List<Guest> findByRoomId(String roomId);
}
