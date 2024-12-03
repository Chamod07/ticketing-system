package com.chamod.ticketingbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/ticket")
public class TicketController {

    @GetMapping(path = "/get-ticket")
    public String getTicket() {
        return "Ticket";
    }

    @GetMapping(path = "/get-name")
    public String getTicketName() {
        return "Chamod";
    }
}
