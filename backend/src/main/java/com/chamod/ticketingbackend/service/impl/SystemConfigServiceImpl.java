package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.SystemConfiguration;
import com.chamod.ticketingbackend.repository.SystemConfigRepository;
import com.chamod.ticketingbackend.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    private SystemConfiguration systemConfiguration;

    private static final Logger logger = LogManager.getLogger(SystemConfigServiceImpl.class);

    public SystemConfigServiceImpl() {
        systemConfiguration = loadConfiguration();
    }

    private SystemConfiguration loadConfiguration() {
        SystemConfiguration systemConfig = null;
        // try to load from file first
        try {
            systemConfig = SystemConfiguration.loadFromFile();
            logger.info("Configuration loaded from file.");
        } catch (Exception e) {
            logger.error("Error loading configuration from file.", e);
        }

        // else load from database if config not found
        if (systemConfig == null && systemConfigRepository != null) {
            systemConfig = systemConfigRepository.findFirstByOrderByIdDesc();
            if (systemConfig != null) {
                logger.info("Configuration loaded from database.");
            } else {
                logger.error("Error loading configuration from database.");
            }
        }

        return systemConfig;
    }

    @Override
    public SystemConfiguration getConfiguration() {
        return systemConfiguration;
    }

    @Override
    public void updateConfiguration(SystemConfiguration newConfig) {
        this.systemConfiguration = newConfig;
        try {
            systemConfiguration.saveToFile();
            logger.info("Configuration saved to file.");
        } catch (IOException e) {
            logger.error("Error saving configuration to file.");
        }

        if (systemConfigRepository != null) {
            systemConfigRepository.save(systemConfiguration);
            logger.info("Configuration saved to database.");
        }
    }
}
