package com.example.smartvotingsystem.controller;
import com.example.smartvotingsystem.dto.*;
import com.example.smartvotingsystem.entity.*;
import com.example.smartvotingsystem.services.*;
import com.example.smartvotingsystem.statistics.Statistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/svs")
public class SmartVotingSystemController {


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
    public void leaveRoom(@PathVariable("guestId") String guestId){
         guestServices.leaveRoom(guestId);
    }

    @DeleteMapping("/endRoom/{roomId}")
    public void endRoom(@PathVariable("roomId") String roomId){
        guestServices.endRoom(roomId);
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

    @GetMapping("/getCurrentStatement/{roomId}")
    public Single<BaseWebResponse<Statement>> getCurrentStatement(@PathVariable ("roomId") String roomId){
        return statementServices.getCurrentStatement(roomId).map(data -> BaseWebResponse.successWithData(data));
    }
    //==========================StatementGuest=========================================
    @GetMapping("/getMean/{statementId}")
    public double getMean(@PathVariable("statementId") String statementId){
        return  statementGuestServices.getMean(statementId);
    }
    @GetMapping("/getMedian/{statementId}")
    public double getMedian(@PathVariable("statementId") String statementId){
        return statementGuestServices.getMedian(statementId);
    }
    @GetMapping("/getMode/{statementId}")
    public int getMode(@PathVariable("statementId") String statementId){
        return statementGuestServices.getMode(statementId);
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
        statementServices.save(statement)
                .map(data -> BaseWebResponse.successWithData(data));
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
    public String removeGuestSocket(@Payload Guest guest){
        System.out.println("Leave Room Guest Hitted" + guest.getGuestId());
        guestServices.leaveRoom(guest.getGuestId());
        return guest.getGuestId();
    }

    @MessageExceptionHandler()
    @MessageMapping("/removeAdminSocket")
    @SendTo("/topic/removeAdminSocket")
    public String removeAdminSocket(@Payload Room room){
        System.out.println("End Room Admin Hitted"+ room.getRoomId());
        statementServices.setNull();
        guestServices.endRoom(room.getRoomId());
        return room.getRoomId();
    }

//    @MessageExceptionHandler()
//    @MessageMapping("/getStatistics")
//    @SendTo("topic/getStatistics")
//    public Statistics getStatistics(@Payload StatementGuest statementGuest){
//        Statistics statistics = new Statistics();
//        statistics.setMean(statementGuestServices.getMean(statementGuest.getStatementId()));
//        statistics.setMedian(statementGuestServices.getMedian(statementGuest.getStatementId()));
//        statistics.setMode(statementGuestServices.getMode(statementGuest.getStatementId()));
//        return statistics;
//    }
}
