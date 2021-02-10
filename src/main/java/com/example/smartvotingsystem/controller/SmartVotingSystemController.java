package com.example.smartvotingsystem.controller;
import com.example.smartvotingsystem.dto.*;
import com.example.smartvotingsystem.entity.Chat;
import com.example.smartvotingsystem.entity.Guest;
import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.entity.Statement;
import com.example.smartvotingsystem.services.GuestServices;
import com.example.smartvotingsystem.services.RoomServices;
import com.example.smartvotingsystem.services.StatementServices;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import rx.schedulers.Schedulers;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/svs")
public class SmartVotingSystemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    RoomServices roomServices;

    @Autowired
    GuestServices guestServices;

    @Autowired
    StatementServices statementServices;

    //==========================Room====================================================

    @PostMapping ("/createRoom")
    public Single<ResponseEntity<Room>> createRoom (@RequestBody Room room){
        return roomServices.save(room)
                .map(room1 -> ResponseEntity.ok(room1));
    }

    @PostMapping ("/checkPassword")
    public Single<Boolean> checkPassword(@RequestBody RoomPassword roomPassword) {
        return roomServices.getPasswordByRoomId(roomPassword);
    }

    @GetMapping ("/findByRoomId/{roomId}")
    public ResponseEntity findByRoomId (@PathVariable ("roomId") String roomId){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/Json");
            return ResponseEntity.ok().headers(headers).body(roomServices.findByRoomId(roomId));
        }
        catch(Exception e) {
            return new ResponseEntity(new ResponseMessage("Room Not found"),HttpStatus.BAD_REQUEST);
        }
    }

    //==========================Guest===================================================

    @GetMapping("/findGuestById/{guestId}")
    public Guest findGuestById(@PathVariable("guestId") String guestId){
        return guestServices.findGuestById(guestId);
    }

    @PostMapping("/joinRoomAsGuest")
    public Single<Guest> joinRoomAsGuest (@RequestBody RoomGuest roomGuest) {
        return guestServices.addGuest(roomGuest);
    }

    @PostMapping("/joinRoomAsAdmin")
    public Single<Guest> joinRoomAsAdmin(@RequestBody RoomAdmin roomAdmin){
        return guestServices.addAdmin(roomAdmin);
    }

    @PostMapping("/addScore")
    public Single<Guest> addScore(@RequestBody Score score){
        return guestServices.addScore(score);
    }

    @GetMapping ("/getGuests/{roomId}")
    public Single<List<Guest>> getGuests (@PathVariable("roomId") String roomId){
        return guestServices.getGuests(roomId);
    }

    //==========================Statement=============================================

    @PostMapping("/createStatement")
    public Single<Statement> createStatement(@RequestBody Statement statement) {
       return statementServices.save(statement).subscribeOn(Schedulers.io());
    }

    //==========================StatementGuest=========================================

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

}
