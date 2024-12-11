package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.service.CustomerService;
import com.chamod.ticketingbackend.service.SystemService;
import com.chamod.ticketingbackend.service.VendorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CustomerService customerService;

    private boolean running = false;
    private boolean paused = false;

    private static final Logger logger = LogManager.getLogger(SystemServiceImpl.class);

    @Override
    public synchronized String startSystem() {
        if (running) {
            logger.warn("System is already running.");
            return "System is already running.";
        }

        if (vendorService.vendorCount() > 0 || customerService.getCustomerCount() > 0 || customerService.getVipCustomerCount() > 0) {
            vendorService.startVendors();
            customerService.startCustomers();
            paused = false;
            running = true;
            logger.info("System started.");
            return "System started.";
        }
        logger.warn("Not enough vendors or customers to start system.");
        return "System not started.";
    }

    @Override
    public synchronized String pauseSystem() {
        if (!running) {
            logger.warn("System is not running. Cannot pause system.");
            return "System is not running. Cannot pause system.";
        }

        vendorService.pauseVendors();
        customerService.pauseCustomers();
        paused = true;
        running = false;
        return "System paused.";

//        logger.info("System paused.");
    }

    @Override
    public synchronized String resumeSystem() {
        if (!paused) {
            logger.warn("System is not paused. Cannot resume system.");
            return "System is not paused. Cannot resume system.";
        }

        vendorService.resumeVendors();
        customerService.resumeCustomers();
        running = true;
        return "System resumed.";
//        logger.info("System resumed.");
    }

    @Override
    public synchronized String stopSystem() {
        if (!running) {
            logger.warn("System is not running. Cannot stop system.");
            return "System is not running. Cannot stop system.";
        }

        vendorService.stopVendors();
        customerService.stopCustomers();
        paused = false;
        running = false;
        return "System stopped.";

//        logger.info("System stopped.");
    }

    @Override
    public synchronized String getState() {
        if (running) {
            return "Running";
        }
        if (paused) {
            return "Paused";
        }
        return "Stopped";
    }
}
