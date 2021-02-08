package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.StatementGuest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementGuestRepository extends MongoRepository<StatementGuest , String> {
}
