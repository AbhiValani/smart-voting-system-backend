package com.example.smartvotingsystem.repository;

import com.example.smartvotingsystem.entity.Statement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rx.Single;

import java.util.List;

@Repository
public interface StatementRepository extends MongoRepository<Statement , String> , CrudRepository<Statement , String> {


    @Query(nativeQuery = true, value = "select * from statement s where s.room_id=?1")
    List<Statement> findStatementsByRoomId(String roomId);
}
