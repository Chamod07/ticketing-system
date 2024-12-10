package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.SystemConfiguration;
import com.chamod.ticketingbackend.repository.SystemConfigRepository;
import com.chamod.ticketingbackend.service.ConfigService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Autowired
    private TicketPoolServiceImpl ticketPoolServiceImpl;

    private SystemConfiguration systemConfiguration;

    private static final Logger logger = LogManager.getLogger(ConfigServiceImpl.class);

    // load the configuration on app launch
    @PostConstruct
    public void initializeConfiguration() {
        systemConfiguration = loadConfiguration();
    }

    @Override
    public SystemConfiguration loadConfiguration() {
        // try to load from file first
        systemConfiguration = SystemConfiguration.loadFromFile();
        if (systemConfiguration != null) {
            logger.info("Configuration-{} loaded from file.", systemConfiguration);
            configureTicketPool();
        } else {
            // else load from database if config file not found
            if (systemConfigRepository != null) {
                systemConfiguration = systemConfigRepository.findFirstByOrderByIdDesc();

                if (systemConfiguration != null) {
                    logger.info("Configuration-{} loaded from database.", systemConfiguration);
                    configureTicketPool();
                } else {
                    logger.error("Error loading configuration from both file and database.");
                }
            }
        }
        return systemConfiguration;
    }

    @Override
    public void updateConfiguration(SystemConfiguration newConfig) {
        systemConfiguration = newConfig;
        try {
            systemConfiguration.saveToFile();
            logger.info("Configuration-{} saved to file.", systemConfiguration.toString());
        } catch (IOException e) {
            logger.error("Error saving configuration to file.");
        }

        if (systemConfigRepository != null) {
            systemConfigRepository.save(systemConfiguration);
            logger.info("Configuration-{} saved to database.", systemConfiguration.toString());
        }
        configureTicketPool();
    }

    private void configureTicketPool() {
        ticketPoolServiceImpl.configure(
                systemConfiguration.getMaxTicketCapacity(),
                systemConfiguration.getTotalTickets()
        );
    }

    @Override
    public SystemConfiguration getConfiguration() {
        return systemConfiguration;
    }
}
