package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.repository.RoomRepository;
import com.example.smartvotingsystem.services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;

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
        return Single.create(singleSubscriber -> {
            Room newRoom = roomRepository.save(room);
            singleSubscriber.onSuccess(newRoom);
        });
    }

    @Override
    public Single<Boolean> getPasswordByRoomId(Room room) {
        return Single.create(singleSubscriber -> {
            Optional<Room> room1 = roomRepository.findById(room.getRoomId());
            Boolean checkPassword = room.getPassword().equals(room1.get().getPassword());
            singleSubscriber.onSuccess(checkPassword);
        });
    }

}
