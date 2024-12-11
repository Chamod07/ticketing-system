package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/tickets")
public class TicketPoolController {

    @Autowired
    private TicketPoolService ticketPoolService;

    @GetMapping(path = "/availability")
    public int availableTickets() {
        return ticketPoolService.getAvailableTickets();
    }

    @GetMapping(path = "/max-capacity")
    public int maxCapacity() {
        return ticketPoolService.getMaxCapacity();
    }


    @GetMapping(path = "/released")
    public int releasedTickets() {
        return ticketPoolService.getReleasedTickets();
    }

    @GetMapping(path = "/purchased")
    public int purchasedTickets() {
        return ticketPoolService.getPurchasedTickets();
    }
}
