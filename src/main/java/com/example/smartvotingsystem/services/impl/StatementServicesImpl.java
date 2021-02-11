package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.entity.Statement;
import com.example.smartvotingsystem.repository.RoomRepository;
import com.example.smartvotingsystem.repository.StatementRepository;
import com.example.smartvotingsystem.services.StatementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;
import rx.schedulers.Schedulers;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class StatementServicesImpl implements StatementServices {

    @Autowired
    StatementRepository statementRepository;

    @Autowired
    RoomRepository roomRepository;

    @Override
    public Single<Statement> save(Statement statement) {
        return Single. <Statement> create(
                singleSubscriber -> {
                    Optional<Room> room = roomRepository.findById(statement.getRoomId());
                    if (room.isPresent()){
                        Statement newStatement = statementRepository.save(statement);
                        singleSubscriber.onSuccess(newStatement);
                        System.out.println("Statement Created");
                    }else{
                        singleSubscriber.onError(new EntityNotFoundException());
                    }
                }).subscribeOn(Schedulers.io());
    }
    @Override
    public Single<List<Statement>> getStatements(String roomId) {
        return  Single.<List<Statement>>create(
                singleSubscriber -> {
                    List<Statement> statementList = statementRepository.findStatementsByRoomId(roomId);
                    singleSubscriber.onSuccess(statementList);
                }).subscribeOn(Schedulers.io());
    }
}
