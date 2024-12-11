package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.SystemConfiguration;

/**
 * Service interface for managing system configurations.
 */
public interface ConfigService {

    /**
     * Loads the current system configuration.
     *
     * @return the current system configuration
     */
    SystemConfiguration loadConfiguration();

    /**
     * Updates the system configuration with the provided new configuration.
     *
     * @param newConfig the new system configuration to be set
     */
    void updateConfiguration(SystemConfiguration newConfig);

    /**
     * Retrieves the current system configuration.
     *
     * @return the current system configuration
     */
    SystemConfiguration getConfiguration();
}