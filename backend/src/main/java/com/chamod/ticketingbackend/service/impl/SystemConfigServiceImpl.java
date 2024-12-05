package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.entity.SystemConfiguration;
import com.chamod.ticketingbackend.repository.SystemConfigRepository;
import com.chamod.ticketingbackend.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {
    @Autowired
    private SystemConfigRepository systemConfigRepository;

    private SystemConfiguration systemConfiguration;

    public SystemConfigServiceImpl() {
        systemConfiguration = loadConfiguration();
    }

    private SystemConfiguration loadConfiguration() {
        try {
            return SystemConfiguration.loadFromFile();
        } catch (Exception e) {
            return systemConfigRepository.save(new SystemConfiguration());
        }
    }

    @Override
    public SystemConfiguration getConfiguration() {
        return null;
    }

    @Override
    public void updateConfiguration(SystemConfiguration newConfig) {

    }
}
