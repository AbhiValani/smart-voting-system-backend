package com.example.smartvotingsystem.services;

import com.example.smartvotingsystem.dto.RoomAdmin;
import com.example.smartvotingsystem.dto.RoomGuest;
import org.springframework.stereotype.Service;
import rx.Single;

@Service
public interface GuestServices {
    Single<RoomGuest> addGuest(RoomGuest roomGuest);
    Single<RoomAdmin> addAdmin(RoomAdmin roomAdmin);
}
