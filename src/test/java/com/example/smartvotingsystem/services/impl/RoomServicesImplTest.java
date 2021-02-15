package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.repository.RoomRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomServicesImplTest {
    //String roomId = "ad7d5340-1fe7-410b-906a-d0a5d1107786";
    @Mock
    RoomRepository roomRepository ;

    @Mock
    RoomServicesImpl roomServices;

    @Before
    void setUp() {
//        room.setPassword("123");
//        room.setRoomDescription("Whitedevils are coming");
//        room.setRoomName("Devil");
    }
    @Test
    void shouldSave() {
        Room room = new Room("Devil" , "WHitedevils are coming" , "123");
        RoomServicesImpl roomServices1 = new RoomServicesImpl(roomRepository);
        roomServices.save(room);
//        Mockito.when(roomRepository , Mockito.times(1)).save;
        Mockito.verify(roomRepository , Mockito.times(1)).save(room);
    }
}