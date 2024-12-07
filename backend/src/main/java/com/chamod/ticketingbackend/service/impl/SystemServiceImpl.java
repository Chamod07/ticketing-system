package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.service.CustomerService;
import com.chamod.ticketingbackend.service.SystemService;
import com.chamod.ticketingbackend.service.VendorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CustomerService customerService;

    private boolean running = false;

    private static final Logger logger = LogManager.getLogger(SystemServiceImpl.class);

    @Override
    public synchronized void startSystem() {
        if (running) {
            logger.warn("System is already running.");
            return;
        }

        vendorService.startVendors();
        customerService.startCustomers();
        running = true;

        logger.info("System started.");
    }

    @Override
    public synchronized void pauseSystem() {
        if (!running) {
            logger.warn("System is not running. Cannot pause system.");
            return;
        }

        vendorService.pauseVendors();
        customerService.pauseCustomers();
        running = false;

        logger.info("System paused.");
    }

    @Override
    public synchronized void resumeSystem() {
        if (running) {
            logger.warn("System is already running. Cannot resume system.");
            return;
        }

        vendorService.resumeVendors();
        customerService.resumeCustomers();
        running = true;

        logger.info("System resumed.");
    }

    @Override
    public synchronized void stopSystem() {
        if (!running) {
            logger.warn("System is not running. Cannot stop system.");
            return;
        }

        vendorService.stopVendors();
        customerService.stopCustomers();
        running = false;

        logger.info("System stopped.");
    }

    @Override
    public synchronized boolean isRunning() {
        return running;
    }
}
