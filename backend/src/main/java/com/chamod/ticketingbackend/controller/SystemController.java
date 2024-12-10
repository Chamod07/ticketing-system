package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.service.SystemService;
import com.chamod.ticketingbackend.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private TicketPoolService ticketPoolService;

    @PostMapping(path = "/start")
    public String startSystem() {
        return systemService.startSystem();
    }

    @PostMapping(path = "/pause")
    public String pauseSystem() {
        return systemService.pauseSystem();
    }

    @PostMapping(path = "/stop")
    public String stopSystem() {
        return systemService.stopSystem();
    }

    @PostMapping(path = "/resume")
    public String resumeSystem() {
        return systemService.resumeSystem();
    }

    @GetMapping(path = "/state")
    public String systemState() {
        return systemService.getState();
    }

    @GetMapping(path = "/available-tickets")
    public int availableTickets() {
        return ticketPoolService.getAvailableTickets();
    }
}
