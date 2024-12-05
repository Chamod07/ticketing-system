package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.model.SystemConfiguration;
import com.chamod.ticketingbackend.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/system-config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @GetMapping
    public SystemConfiguration getSystemConfig() {
        return systemConfigService.getConfiguration();
    }

    @PostMapping
    public SystemConfiguration updateConfiguration(@RequestBody SystemConfiguration newConfig) {
        systemConfigService.updateConfiguration(newConfig);
        return newConfig;
    }
}
