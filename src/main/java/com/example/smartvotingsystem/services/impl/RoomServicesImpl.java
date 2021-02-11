package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.dto.RoomPassword;
import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.repository.RoomRepository;
import com.example.smartvotingsystem.services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;
import rx.schedulers.Schedulers;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class RoomServicesImpl implements RoomServices {

    @Autowired
    RoomRepository roomRepository;


    @Override
    public Single<Room> save(Room room) {
        return Single.<Room>create(singleSubscriber -> {
            singleSubscriber.onSuccess(roomRepository.save(room));
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> getPasswordByRoomId(RoomPassword roomPassword) {
        return Single.<Boolean>create(singleSubscriber -> {
            Optional<Room> room1 = roomRepository.findById(roomPassword.getRoomId());
            if (room1.isPresent()){
                Boolean checkPassword = roomPassword.getPassword().equals(room1.get().getPassword());
                singleSubscriber.onSuccess(checkPassword);
            }else{
                singleSubscriber.onError(new EntityNotFoundException());
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Room findByRoomId(String roomId) {
        return roomRepository.findById(roomId).get();
    }

}
