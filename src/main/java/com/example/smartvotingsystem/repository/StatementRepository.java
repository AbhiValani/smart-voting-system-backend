package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Statement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends MongoRepository<Statement , String> {
}
