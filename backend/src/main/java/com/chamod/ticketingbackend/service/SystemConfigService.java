package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.SystemConfiguration;

public interface SystemConfigService {
    SystemConfiguration getConfiguration();

    void updateConfiguration(SystemConfiguration newConfig);
}
