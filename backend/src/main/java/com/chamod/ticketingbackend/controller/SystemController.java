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
}
