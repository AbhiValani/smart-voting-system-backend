package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.dto.RoomAdmin;
import com.example.smartvotingsystem.dto.RoomGuest;
import com.example.smartvotingsystem.dto.Score;
import com.example.smartvotingsystem.entity.Guest;
import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.entity.Statement;
import com.example.smartvotingsystem.repository.GuestRepository;
import com.example.smartvotingsystem.repository.RoomRepository;
import com.example.smartvotingsystem.repository.StatementRepository;
import com.example.smartvotingsystem.services.GuestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Completable;
import rx.Single;
import rx.schedulers.Schedulers;

import javax.persistence.EntityNotFoundException;
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

    @Override
    public Single<Guest> addGuest(RoomGuest roomGuest) {
        return addGuestInRoom(roomGuest);
    }

    private Single<Guest> addGuestInRoom(RoomGuest roomGuest) {
        return Single.<Guest> create(singleSubscriber -> {
            Guest guest = guestRepository.save(toGuest(roomGuest));
            singleSubscriber.onSuccess(guest);
        }).subscribeOn(Schedulers.io());
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
        return Single.<Guest> create(singleSubscriber -> {
            Guest guest = guestRepository.save(AdminToGuest(roomAdmin));
            singleSubscriber.onSuccess(guest);
        }).subscribeOn(Schedulers.io());
    }

    private Guest AdminToGuest(RoomAdmin roomAdmin) {
        Guest guest = new Guest();
        guest.setGuestId(UUID.randomUUID().toString());
        guest.setGuestName("Admin");
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
        return Single.create(singleSubscriber -> {
           Optional<Guest> guest = guestRepository.findById(guestId);
           if (guest.isPresent()){
               singleSubscriber.onSuccess(guest.get());
           }else{
               singleSubscriber.onError(new EntityNotFoundException());
           }
        });
    }

    private Single<Guest> addScoreToGuestRepository(Score score) {
        return Single.<Guest> create(singleSubscriber -> {
            Optional<Guest> guest = guestRepository.findById(score.getGuestId());
            if (guest.isPresent()) {
                Guest guest1 = guestRepository.save(optionalGuestToGuest(guest, score));
                singleSubscriber.onSuccess(guest1);
            }
            else{
                singleSubscriber.onError(new EntityNotFoundException());
            }
        }).subscribeOn(Schedulers.io());
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
        return Single.<List<Guest>> create(singleSubscriber -> {
            List<Guest> guestList = guestRepository.findByRoomId(roomId);
            singleSubscriber.onSuccess(guestList);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable leaveRoom(String guestId) {
        return Completable.create(completableSubscriber -> {
            Optional<Guest> guest = guestRepository.findById(guestId);
            if (guest.isPresent()){
                guestRepository.delete(guest.get());
                completableSubscriber.onCompleted();
            }else{
                completableSubscriber.onError(new EntityNotFoundException());
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable endRoom(String roomId) {
        return Completable.create(completableSubscriber -> {

            Optional<Room> room = roomRepository.findById(roomId);
            if (room.isPresent()){
                roomRepository.deleteById(roomId);
            }else{
                completableSubscriber.onError(new EntityNotFoundException());
            }

            List<Guest> guestList = guestRepository.findByRoomId(roomId);
            for (Guest guest : guestList){
                guestRepository.delete(guest);
            }

            List<Statement> statementList = statementRepository.findStatementsByRoomId(roomId);
            for (Statement statement : statementList){
                statementRepository.delete(statement);
            }
            completableSubscriber.onCompleted();
        }).subscribeOn(Schedulers.io());
    }
}
