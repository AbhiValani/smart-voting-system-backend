package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Guest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GuestRepository extends MongoRepository<String , Guest> {
}
