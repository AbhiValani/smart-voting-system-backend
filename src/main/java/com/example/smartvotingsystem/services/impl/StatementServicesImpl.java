package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.entity.Guest;
import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.entity.Statement;
import com.example.smartvotingsystem.repository.GuestRepository;
import com.example.smartvotingsystem.repository.RoomRepository;
import com.example.smartvotingsystem.repository.StatementRepository;
import com.example.smartvotingsystem.services.StatementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;
import rx.schedulers.Schedulers;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatementServicesImpl implements StatementServices {

    @Autowired
    StatementRepository statementRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    GuestRepository guestRepository;

    String lastStatementId;

    @Override
    public Single<Statement> save(Statement statement) {
        return Single. <Statement> create(
                singleSubscriber -> {
                    Optional<Room> room = roomRepository.findById(statement.getRoomId());
                    if (room.isPresent()){
                        if (lastStatementId != null) {
                            System.out.println("LastStatementId" + lastStatementId);
                            Statement statement1 = statementRepository.findByStatementId(lastStatementId);
                            statement1.setCurrent(false);
                            statementRepository.save(statement1);
                        }
                        statement.setCurrent(true);
                        statement.setAvgScore(0);
                        statement.setMedianScore(0);
                        Statement newStatement = statementRepository.save(statement);
                        lastStatementId = statement.getStatementId();
                        singleSubscriber.onSuccess(newStatement);
                        System.out.println("Statement Created");

                        // Set All Guest -> isVoted False;
                        List<Guest> guestList = guestRepository.findByRoomId(statement.getRoomId());
                        for (Guest guest : guestList){
                            guest.setVoted(false);
                            guest.setGuestScore(0);
                            guestRepository.save(guest);
                        }
                        System.out.println("Guest Updated");
                    }else{
                        singleSubscriber.onError(new EntityNotFoundException());
                    }
                }
        ).subscribeOn(Schedulers.io());
    }
    @Override
    public Single<List<Statement>> getStatements(String roomId) {
        return  Single.<List<Statement>>create(
                singleSubscriber -> {
                    List<Statement> statementList = statementRepository.findStatementsByRoomId(roomId);
                    List<Statement> statementList1 = new ArrayList<>();
                    for (Statement statement : statementList){
                        if (!statement.isCurrent()){
                            statementList1.add(statement);
                        }
                    }
                    singleSubscriber.onSuccess(statementList1);
                }
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public void setNull(){
        lastStatementId = null;
        System.out.println("SetNull");
    }

    @Override
    public Single<Statement> getCurrentStatement(String roomId) {
        return Single. <Statement> create(
                singleSubscriber -> {
                    Statement statement = new Statement();
                     if (lastStatementId != null) {
                         statement = statementRepository.findByStatementId(lastStatementId);
                         singleSubscriber.onSuccess(statement);
                     }else{
                         singleSubscriber.onError(new EntityNotFoundException());
                     }
                }
        ).subscribeOn(Schedulers.io());
    }
}
