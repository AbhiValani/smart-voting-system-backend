package com.example.smartvotingsystem.controller;
import com.example.smartvotingsystem.dto.RoomAdmin;
import com.example.smartvotingsystem.dto.RoomGuest;
import com.example.smartvotingsystem.entity.Chat;
import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.entity.Statement;
import com.example.smartvotingsystem.services.GuestServices;
import com.example.smartvotingsystem.services.RoomServices;
import com.example.smartvotingsystem.services.StatementServices;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import rx.schedulers.Schedulers;

import java.lang.invoke.MethodHandles;

@Slf4j
@RestController
@RequestMapping(value = "/svs")
public class SmartVotingSystemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    //==========================Room====================================================
    @Autowired
    RoomServices roomServices;


    @PostMapping ("/createRoom")
    public Single<Room> createRoom (@RequestBody Room room){
        LOGGER.info(room.toString());
        return roomServices.save(room).subscribeOn(Schedulers.io());
    }

    @PostMapping ("/checkPassword")
    public Single<Boolean> checkPassword(@RequestBody Room room) {
        System.out.println("API Hitted");
        return roomServices.getPasswordByRoomId(room);
    }

    //==================================================================================

    //==========================Guest===================================================

    @Autowired
    GuestServices guestServices;

    @PostMapping("/joinRoomAsGuest")
    public Single<RoomGuest> joinRoomAsGuest (@RequestBody RoomGuest roomGuest) {
        return guestServices.addGuest(roomGuest).subscribeOn(Schedulers.io());
    }

    @PostMapping("/joinRoomAsAdmin")
    public Single<RoomAdmin> joinRoomAsAdmin(@RequestBody RoomAdmin roomAdmin){
        return guestServices.addAdmin(roomAdmin).subscribeOn(Schedulers.io());
    }

    //==================================================================================

    //==========================Statement===============================================
    @Autowired
    StatementServices statementServices;

    @PostMapping("/createStatement")
    public Single<Statement> createStatement(@RequestBody Statement statement) {
       return statementServices.save(statement).subscribeOn(Schedulers.io());
    }

    //=================================================================================

    //==========================StatementGuest=========================================

    //=================================================================================

    //==========================Chat===================================================

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public Chat register(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("guestName", chat.getGuestName());
        return chat;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public Chat sendMessage(@Payload Chat chat) {
        return chat;
    }

    //=================================================================================
}
