package com.example.smartvotingsystem.controller;
import com.example.smartvotingsystem.entity.Room;
import com.example.smartvotingsystem.services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/svs")
public class SmartVotingSystemController {

    @Autowired
    RoomServices roomServices;

    @PostMapping
    Room insert (@RequestBody Room room){
        return  roomServices.insert(room);
    }

}
