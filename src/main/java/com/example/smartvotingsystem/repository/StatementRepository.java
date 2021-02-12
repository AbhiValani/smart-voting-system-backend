package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Statement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends MongoRepository<Statement , String> , CrudRepository<Statement , String> {
    List<Statement> findByRoomId(String roomId);

    List<Statement> findStatementsByRoomId(String roomId);
}
