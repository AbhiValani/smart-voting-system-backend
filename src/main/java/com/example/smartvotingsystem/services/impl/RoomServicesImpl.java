package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.dto.RoomPassword;
import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.repository.RoomRepository;
import com.example.smartvotingsystem.services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rx.Single;
import rx.schedulers.Schedulers;

import java.util.Optional;

@Service
public class RoomServicesImpl implements RoomServices {

    @Autowired
    RoomRepository roomRepository;


    @Override
    public Single<Room> save(Room room) {
        return addRoomToRepository(room);
    }

    private Single<Room> addRoomToRepository(Room room) {
        return Single.<Room>create(singleSubscriber -> {
            Room newRoom = roomRepository.save(room);
            singleSubscriber.onSuccess(newRoom);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> getPasswordByRoomId(RoomPassword roomPassword) {
        return Single.<Boolean>create(singleSubscriber -> {
            Optional<Room> room1 = roomRepository.findById(roomPassword.getRoomId());
            Boolean checkPassword = roomPassword.getPassword().equals(room1.get().getPassword());
            singleSubscriber.onSuccess(checkPassword);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Room findByRoomId(String roomId) {
        return roomRepository.findById(roomId).get();
    }

}
