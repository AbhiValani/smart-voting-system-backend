package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.dto.BaseWebResponse;
import com.example.smartvotingsystem.dto.RoomAdmin;
import com.example.smartvotingsystem.dto.RoomGuest;
import com.example.smartvotingsystem.dto.Score;
import com.example.smartvotingsystem.entity.*;
import com.example.smartvotingsystem.exception.ErrorCode;
import com.example.smartvotingsystem.repository.*;
import com.example.smartvotingsystem.services.GuestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;
import rx.schedulers.Schedulers;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuestServicesImpl implements GuestServices {

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    StatementRepository statementRepository;

    @Autowired
    StatementGuestRepository statementGuestRepository;

    @Autowired
    ChatRepository chatRepository;

    @Override
    public Single<Guest> addGuest(RoomGuest roomGuest) {
        return addGuestInRoom(roomGuest);
    }

    private Single<Guest> addGuestInRoom(RoomGuest roomGuest) {
        return Single.<Guest> create(
                singleSubscriber -> {
                    Optional<Room> room = roomRepository.findById(roomGuest.getRoomId());
                    if(room.isPresent()){
                        Guest guest = guestRepository.save(toGuest(roomGuest));
                        singleSubscriber.onSuccess(guest);
                    }else{
                       singleSubscriber.onError(new EntityNotFoundException());
                    }
                }
        ).subscribeOn(Schedulers.io());
    }
    private Guest toGuest(RoomGuest roomGuest) {
        Guest guest = new Guest();
        guest.setGuestId(UUID.randomUUID().toString());
        guest.setGuestName(roomGuest.getGuestName());
        guest.setVoted(false);
        guest.setGuestScore(0);
        guest.setRoomId(roomGuest.getRoomId());
        return guest;
    }
    @Override
    public Single<Guest> addAdmin(RoomAdmin roomAdmin) {
        return addAdminToRoom(roomAdmin);
    }

    private Single<Guest> addAdminToRoom(RoomAdmin roomAdmin) {
        return Single.<Guest> create(
                singleSubscriber -> {
                    Optional<Room> room = roomRepository.findById(roomAdmin.getRoomId());
                    List<Guest> guestList = guestRepository.findByRoomId(roomAdmin.getRoomId());
                    boolean admin = false;
                    for (Guest guest : guestList){
                        if (guest.getGuestName().equals("admin")){
                            admin = true;
                            break;
                        }
                    }
                    if (room.isPresent() && !admin){
                        Guest guest = guestRepository.save(AdminToGuest(roomAdmin));
                        singleSubscriber.onSuccess(guest);
                    }else{
                        singleSubscriber.onError(new EntityNotFoundException());
                    }
                 }
        ).subscribeOn(Schedulers.io());
    }

    private Guest AdminToGuest(RoomAdmin roomAdmin) {
        Guest guest = new Guest();
        guest.setGuestId(UUID.randomUUID().toString());
        guest.setGuestName("admin");
        guest.setVoted(false);
        guest.setGuestScore(0);
        guest.setRoomId(roomAdmin.getRoomId());
        return guest;
    }

    @Override
    public Single<Guest> addScore(Score score) {
        return addScoreToGuestRepository(score);
    }

    @Override
    public Single<Guest> findGuestById(String guestId) {
        return Single. <Guest> create(
                singleSubscriber -> {
                   Optional<Guest> guest = guestRepository.findById(guestId);
                   if (guest.isPresent()){
                       singleSubscriber.onSuccess(guest.get());
                   }else{
                       singleSubscriber.onError(new EntityNotFoundException());
                   }
                }
        ).subscribeOn(Schedulers.io());
    }

    private Single<Guest> addScoreToGuestRepository(Score score) {
        return Single.<Guest> create(
                singleSubscriber -> {
                    Optional<Guest> guest = guestRepository.findById(score.getGuestId());
                    if (guest.isPresent()) {
                        Guest guest1 = guestRepository.save(optionalGuestToGuest(guest, score));
                        StatementGuest statementGuest = new StatementGuest();
                        statementGuest.setGuestId(score.getGuestId());
                        statementGuest.setStatementId(score.getStatementId());
                        statementGuest.setScore(score.getScore());
                        StatementGuest statementGuest1 = statementGuestRepository.save(statementGuest);
                        System.out.println("Add Score-------" + score.toString());
                        singleSubscriber.onSuccess(guest1);
                    }
                    else{
                        singleSubscriber.onError(new EntityNotFoundException());
                    }
                }
        ).subscribeOn(Schedulers.io());
    }

    private Guest optionalGuestToGuest(Optional<Guest> guest , Score score) {
        Guest newGuest = new Guest();
        newGuest.setGuestId(guest.get().getGuestId());
        newGuest.setRoomId(guest.get().getRoomId());
        newGuest.setGuestScore(score.getScore());
        newGuest.setVoted(true);
        newGuest.setGuestName(guest.get().getGuestName());
        return newGuest;
    }

    @Override
    public Single<List<Guest>> getGuests(String roomId) {
        return Single.<List<Guest>> create(
                singleSubscriber -> {
                    Optional<Room> room = roomRepository.findById(roomId);
                    if (room.isPresent()){
                        List<Guest> guestList = guestRepository.findByRoomId(roomId);
                        singleSubscriber.onSuccess(guestList);
                    }else{
                        singleSubscriber.onError(new EntityNotFoundException());
                    }
                }
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public void leaveRoom(String guestId) {
        System.out.println("Coming");
        guestRepository.deleteById(guestId);
    }

    @Override
    public void endRoom(String roomId) {
        System.out.println("END-ROOM" + roomId);
        Optional<Room> room = roomRepository.findById(roomId);
        System.out.println(room.toString());
        if (room.isPresent()){
            System.out.println("EndRoom=====");
            guestRepository.deleteByRoomId(roomId);
            statementRepository.deleteByRoomId(roomId);
            chatRepository.deleteByRoomId(roomId);
            roomRepository.deleteById(roomId);
        }else{
            System.out.println(BaseWebResponse.error(ErrorCode.ENTITY_NOT_FOUND));
        }
    }
}
