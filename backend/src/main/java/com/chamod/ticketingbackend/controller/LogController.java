package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.model.Log;
import com.chamod.ticketingbackend.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing logs.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/log")
public class LogController {

    private final LogService logService;

    /**
     * Constructor for LogController.
     *
     * @param logService the service to manage logs
     */
    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * GET /latest : Get the latest logs.
     *
     * @return a list of the latest logs
     */
    @GetMapping(path = "/latest")
    public List<Log> getLatestLog() {
        return logService.getLogs();
    }

}