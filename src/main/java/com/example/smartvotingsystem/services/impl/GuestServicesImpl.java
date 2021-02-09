package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.dto.RoomAdmin;
import com.example.smartvotingsystem.dto.RoomGuest;
import com.example.smartvotingsystem.entity.Guest;
import com.example.smartvotingsystem.repository.GuestRepository;
import com.example.smartvotingsystem.services.GuestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;

import java.util.UUID;

@Service
public class GuestServicesImpl implements GuestServices {

    @Autowired
    GuestRepository guestRepository;

    @Override
    public Single<RoomGuest> addGuest(RoomGuest roomGuest) {
        return addGuestInRoom(roomGuest);
    }

    private Single<RoomGuest> addGuestInRoom(RoomGuest roomGuest) {
        return Single.create(singleSubscriber -> {
            Guest guest = guestRepository.save(toGuest(roomGuest));
            RoomGuest newRoomGuest = new RoomGuest(roomGuest.getRoomName() , guest.getGuestName() , guest.getRoomId());
            singleSubscriber.onSuccess(newRoomGuest);
        });
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
    public Single<RoomAdmin> addAdmin(RoomAdmin roomAdmin) {
        return addAdminToRoom(roomAdmin);
    }

    private Single<RoomAdmin> addAdminToRoom(RoomAdmin roomAdmin) {
        return Single.create(singleSubscriber -> {
            Guest guest = guestRepository.save(AdminToGuest(roomAdmin));
            RoomAdmin newRandomAdmin = new RoomAdmin(guest.getRoomId() , roomAdmin.getRoomName());
            singleSubscriber.onSuccess(newRandomAdmin);
        });
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

}
