package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing system operations.
 */
@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * Starts the system.
     *
     * @return a message indicating the system has started.
     */
    @PostMapping(path = "/start")
    public String startSystem() {
        return systemService.startSystem();
    }

    /**
     * Pauses the system.
     *
     * @return a message indicating the system has been paused.
     */
    @PostMapping(path = "/pause")
    public String pauseSystem() {
        return systemService.pauseSystem();
    }

    /**
     * Stops the system.
     *
     * @return a message indicating the system has been stopped.
     */
    @PostMapping(path = "/stop")
    public String stopSystem() {
        return systemService.stopSystem();
    }

    /**
     * Resumes the system.
     *
     * @return a message indicating the system has resumed.
     */
    @PostMapping(path = "/resume")
    public String resumeSystem() {
        return systemService.resumeSystem();
    }

    /**
     * Retrieves the current state of the system.
     *
     * @return the current state of the system.
     */
    @GetMapping(path = "/state")
    public String systemState() {
        return systemService.getState();
    }

}