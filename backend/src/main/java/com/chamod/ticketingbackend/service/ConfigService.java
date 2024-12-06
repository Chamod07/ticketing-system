package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.SystemConfiguration;

public interface ConfigService {
    SystemConfiguration loadConfiguration();

    void updateConfiguration(SystemConfiguration newConfig);

    SystemConfiguration getConfiguration();
}
