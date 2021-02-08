package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Guest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends MongoRepository<Guest , String> {
}
