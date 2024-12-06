package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.SystemConfiguration;
import com.chamod.ticketingbackend.model.Vendor;
import com.chamod.ticketingbackend.repository.VendorRepository;
import com.chamod.ticketingbackend.service.ConfigService;
import com.chamod.ticketingbackend.service.TicketPoolService;
import com.chamod.ticketingbackend.service.VendorRunner;
import com.chamod.ticketingbackend.service.VendorService;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private ConfigService configService;

    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Vendor> vendors = new ArrayList<>();
    private boolean running = false;
    private final Logger logger = LogManager.getLogger(VendorServiceImpl.class);

    @PostConstruct
    public void initializeVendors() {
        syncVendorsWithDatabase();
    }

    @Override
    public void addVendor() {
        SystemConfiguration systemConfiguration = configService.getConfiguration();

        Vendor vendor = new Vendor();
        vendor.setTicketsPerRelease(systemConfiguration.getTicketReleaseRate());
        vendor.setReleaseInterval(1); // Default interval of 1 second

        vendorRepository.save(vendor);

        vendors.add(vendor);

        Thread vendorThread = new Thread(new VendorRunner(vendor, ticketPoolService));
        vendorThreads.add(vendorThread);

        logger.info("Vendor-{} added but thread not started.", vendor.getVendorId());
    }

    @Override
    public void removeVendor() {
        syncVendorsWithDatabase();

        if (!vendors.isEmpty() && vendorRepository != null) {
            int lastIndex = (vendors.size()-1);
            // Remove vendor thread
            Thread threadToRemove = vendorThreads.get(lastIndex);
            threadToRemove.interrupt();
            vendorThreads.remove(threadToRemove);

            // Remove vendor from database
            Vendor vendorToRemove = vendors.get(lastIndex);
            vendorRepository.delete(vendorToRemove);
            vendors.remove(vendorToRemove);

            logger.info("Vendor-{} removed.", vendorToRemove.getVendorId());
        } else {
            logger.warn("No vendors to remove.");
        }
    }

    private void syncVendorsWithDatabase() {
        vendors.clear();
        vendors.addAll(vendorRepository.findAll());
        for (Vendor vendor : vendors) {
            Thread vendorThread = new Thread(new VendorRunner(vendor, ticketPoolService));
            vendorThreads.add(vendorThread);
        }

        logger.info("Synchronized vendors with database. Total vendors: {}", vendors.size());
    }

//    public void startVendors() {
//        if (running) {
//            logger.warn("Vendors are already running.");
//            return;
//        }
//        running = true;
//        for (VendorRunner runner : vendors) {
//            Thread thread = new Thread(runner);
//            vendorThreads.add(thread);
//            thread.start();
//        }
//        logger.info("Vendors started.");
//    }
//
//    public void pauseVendors() {
//        if (!running) {
//            logger.warn("Vendors are not running.");
//            return;
//        }
//        for (VendorRunner runner : vendors) {
//            runner.pause();
//        }
//        running = false;
//        logger.info("Vendors paused.");
//    }
//
//    public void stopVendors() {
//        for (VendorRunner runner : vendors) {
//            runner.stop();
//        }
//        for (Thread thread : vendorThreads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                logger.error("Error stopping vendor thread");
//            }
//        }
//        vendorThreads.clear();
//        running = false;
//        logger.info("Vendors stopped.");
//    }
}
