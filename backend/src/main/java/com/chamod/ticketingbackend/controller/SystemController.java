package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @PostMapping(path = "/start")
    public String startSystem() {
        systemService.startSystem();
        return "System started successfully.";
    }

    @PostMapping(path = "/pause")
    public String pauseSystem() {
        systemService.pauseSystem();
        return "System paused successfully.";
    }

    @PostMapping(path = "/stop")
    public String stopSystem() {
        systemService.stopSystem();
        return "System stopped successfully.";
    }

    @PostMapping(path = "/resume")
    public String resumeSystem() {
        systemService.resumeSystem();
        return "System resumed successfully.";
    }
}
