package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.model.SystemConfiguration;
import com.chamod.ticketingbackend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/system-config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping
    public SystemConfiguration getSystemConfig() {
        return configService.getConfiguration();
    }

    @PostMapping
    public SystemConfiguration updateConfiguration(@RequestBody SystemConfiguration newConfig) {
        configService.updateConfiguration(newConfig);
        return newConfig;
    }
}
