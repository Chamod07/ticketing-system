package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.model.SystemConfiguration;
import com.chamod.ticketingbackend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing system configuration.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/system-config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * Retrieves the current system configuration.
     *
     * @return the current system configuration
     */
    @GetMapping
    public SystemConfiguration getSystemConfig() {
        return configService.loadConfiguration();
    }

    /**
     * Updates the system configuration.
     *
     * @param newConfig the new system configuration
     * @return the updated system configuration
     */
    @PostMapping
    public SystemConfiguration updateConfiguration(@RequestBody SystemConfiguration newConfig) {
        configService.updateConfiguration(newConfig);
        return newConfig;
    }
}
