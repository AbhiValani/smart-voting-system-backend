package com.example.smartvotingsystem.controller;
import com.example.smartvotingsystem.dto.*;
import com.example.smartvotingsystem.entity.*;
import com.example.smartvotingsystem.services.*;
import com.example.smartvotingsystem.statistics.Statistics;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import rx.schedulers.Schedulers;

import java.lang.invoke.MethodHandles;
import java.net.URI;
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

    @Autowired
    ChatServices chatServices;

    @Autowired
    StatementGuestServices statementGuestServices;
    //==========================Room====================================================

    @PostMapping ("/createRoom")
    public Single<BaseWebResponse<Room>> createRoom (@RequestBody Room room){

        return roomServices.save(room)
                .map(data -> BaseWebResponse.successWithData(data));
    }

    @PostMapping ("/checkPassword")
    public Single<BaseWebResponse<Boolean>> checkPassword(@RequestBody RoomPassword roomPassword) {
        return roomServices.getPasswordByRoomId(roomPassword)
                .map(data -> BaseWebResponse.successWithData(data));
    }

    @GetMapping ("/findByRoomId/{roomId}")
    public Single<Room> findByRoomId (@PathVariable ("roomId") String roomId){
        return roomServices.findByRoomId(roomId);
    }

    //==========================Guest===================================================

    @GetMapping("/findGuestById/{guestId}")
    public Single<BaseWebResponse<Guest>> findGuestById(@PathVariable("guestId") String guestId){
        return guestServices.findGuestById(guestId)
                .map(data -> BaseWebResponse.successWithData(data));
    }

    @PostMapping("/joinRoomAsGuest")
    public Single<BaseWebResponse<Guest>> joinRoomAsGuest (@RequestBody RoomGuest roomGuest) {
        System.out.println("Room Guest Hitted");
        return guestServices.addGuest(roomGuest)
                .map(data -> BaseWebResponse.successWithData(data));
    }



    @PostMapping("/joinRoomAsAdmin")
    public Single<BaseWebResponse<Guest>> joinRoomAsAdmin(@RequestBody RoomAdmin roomAdmin){
        return guestServices.addAdmin(roomAdmin)
                .map(data -> BaseWebResponse.successWithData(data));
    }

    @PostMapping("/addScore")
    public Single<BaseWebResponse<Guest>> addScore(@RequestBody Score score){
        return guestServices.addScore(score)
                .map(data -> BaseWebResponse.successWithData(data));
    }

    @GetMapping ("/getGuests/{roomId}")
    public Single<BaseWebResponse<List<Guest>>> getGuests (@PathVariable("roomId") String roomId){
        return guestServices.getGuests(roomId)
                .map(data -> BaseWebResponse.successWithData(data));
    }

    @DeleteMapping("/leaveRoom/{guestId}")
    public Single<BaseWebResponse> leaveRoom(@PathVariable("guestId") String guestId){
        return guestServices.leaveRoom(guestId)
                .toSingle(() -> BaseWebResponse.successWithData(guestId));
    }

    @DeleteMapping("/endRoom/{roomId}")
    public Single<BaseWebResponse> endRoom(@PathVariable("roomId") String roomId){
        return guestServices.endRoom(roomId)
                .toSingle(() -> BaseWebResponse.successWithData(roomId));
    }
    //==========================Statement=============================================

    @PostMapping("/createStatement")
    public Single<BaseWebResponse<Statement>> createStatement(@RequestBody Statement statement) {
       return statementServices.save(statement)
               .map(data -> BaseWebResponse.successWithData(data));
    }
    @GetMapping("/getStatements/{roomId}")
    public Single<BaseWebResponse<List<Statement>>> getStatements(@PathVariable("roomId") String roomId){
        return statementServices.getStatements(roomId)
                .map(data -> BaseWebResponse.successWithData(data));
    }
    //==========================StatementGuest=========================================
    @GetMapping("/getMean/{statementId}")
    public Single<BaseWebResponse<Double>> getMean(@PathVariable("statementId") String statementId){
        return statementGuestServices.getMean(statementId)
                .map(data -> BaseWebResponse.successWithData(data));
    }

    @GetMapping("/getMedian/{statementId}")
    public Single<BaseWebResponse<Double>> getMedian(@PathVariable("statementId") String statementId){
        return statementGuestServices.getMedian(statementId)
                .map(data -> BaseWebResponse.successWithData(data));
    }

    @GetMapping("/getMode/{statementId}")
    public Single<BaseWebResponse<Integer>> getMode(@PathVariable("statementId") String statementId){
        return statementGuestServices.getMode(statementId)
                .map(data -> BaseWebResponse.successWithData(data));
    }
    //==========================Chat===================================================

    @GetMapping("/getChats/{roomId}")
    public Single<BaseWebResponse<List<Chat>>> getChats(@PathVariable("roomId") String roomId){
        return chatServices.getChatsByRoomId(roomId)
                .map(data -> BaseWebResponse.successWithData(data));
    }

    //===============================WebSocket===========================================
    @MessageExceptionHandler()
    @MessageMapping("/chat-send")
    @SendTo("/topic/chat")
    public Chat sendMessage( @Payload Chat chat) {
        System.out.println(chat.toString());
        Chat chat1 = chatServices.addChat(chat);
        return chat;
    }


    @MessageExceptionHandler()
    @MessageMapping("/statement")
    @SendTo("/topic/statement")
    public Statement sendStatement (@Payload Statement statement){
        System.out.println(statement.toString());
        return statement;
    }


    @MessageExceptionHandler()
    @MessageMapping("/joinRoomSocket")
    @SendTo("/topic/joinRoomSocket")
    public Guest joinRoomSocket(@Payload Guest guest){
        System.out.println("JoinRoomSocket Hitted");
        return guest;
    }

    @MessageExceptionHandler()
    @MessageMapping("/removeGuestSocket")
    @SendTo("/topic/removeGuestSocket")
    public String removeGuestSocket(@Payload String guestId){
        System.out.println("Leave Room Guest Hitted");
        guestServices.leaveRoom(guestId);
        return guestId;
    }


}
