package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing ticket pool operations.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/tickets")
public class TicketPoolController {

    @Autowired
    private TicketPoolService ticketPoolService;

    /**
     * Endpoint to get the number of available tickets.
     *
     * @return the number of available tickets
     */
    @GetMapping(path = "/availability")
    public int availableTickets() {
        return ticketPoolService.getAvailableTickets();
    }

    /**
     * Endpoint to get the maximum capacity of tickets.
     *
     * @return the maximum capacity of tickets
     */
    @GetMapping(path = "/max-capacity")
    public int maxCapacity() {
        return ticketPoolService.getMaxCapacity();
    }

    /**
     * Endpoint to get the number of released tickets.
     *
     * @return the number of released tickets
     */
    @GetMapping(path = "/released")
    public int releasedTickets() {
        return ticketPoolService.getReleasedTickets();
    }

    /**
     * Endpoint to get the number of purchased tickets.
     *
     * @return the number of purchased tickets
     */
    @GetMapping(path = "/purchased")
    public int purchasedTickets() {
        return ticketPoolService.getPurchasedTickets();
    }
}