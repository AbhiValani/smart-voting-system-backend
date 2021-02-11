package com.example.smartvotingsystem.services;

import com.example.smartvotingsystem.dto.RoomPassword;
import com.example.smartvotingsystem.entity.Room;
import org.springframework.stereotype.Service;
import rx.Single;

@Service
public interface RoomServices {
    Single<Room> save (Room room);

    Single<Boolean> getPasswordByRoomId(RoomPassword roomPassword);

    Room findByRoomId(String roomId);
}
