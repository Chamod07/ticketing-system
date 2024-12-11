package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.model.Log;
import com.chamod.ticketingbackend.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/log")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping(path = "/latest")
    public List<Log> getLatestLog() {
        return logService.getLogs();
    }

}
