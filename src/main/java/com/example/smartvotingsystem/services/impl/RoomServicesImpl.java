package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.repository.RoomRepository;
import com.example.smartvotingsystem.services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServicesImpl implements RoomServices {

    @Autowired
    RoomRepository roomRepository;

    @Override
    public Room insert(Room room) {
        return roomRepository.insert(room);
    }
}
