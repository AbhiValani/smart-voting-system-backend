package com.example.smartvotingsystem.services;

import com.example.smartvotingsystem.dto.RoomAdmin;
import com.example.smartvotingsystem.dto.RoomGuest;
import com.example.smartvotingsystem.dto.Score;
import com.example.smartvotingsystem.entity.Guest;
import org.springframework.stereotype.Service;
import rx.Single;

import java.util.List;

@Service
public interface GuestServices {
    Single<Guest> addGuest(RoomGuest roomGuest);
    Single<Guest> addAdmin(RoomAdmin roomAdmin);
    Single<Guest> addScore(Score score);

    Single<Guest> findGuestById(String guestId);

    Single<List<Guest>> getGuests(String roomId);

    void leaveRoom(String guestId);

    void endRoom(String roomId);
}
