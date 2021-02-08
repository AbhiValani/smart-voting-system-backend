package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.StatementGuest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatementGuestRepository extends MongoRepository<String , StatementGuest> {
}
