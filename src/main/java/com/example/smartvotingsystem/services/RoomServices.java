package com.example.smartvotingsystem.services;

import com.example.smartvotingsystem.entity.Room;
import org.springframework.stereotype.Service;

@Service
public interface RoomServices {
    Room insert (Room room);
}
