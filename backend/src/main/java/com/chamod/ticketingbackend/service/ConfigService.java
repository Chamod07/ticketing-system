package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.SystemConfiguration;

public interface ConfigService {
    SystemConfiguration getConfiguration();

    void updateConfiguration(SystemConfiguration newConfig);
}
