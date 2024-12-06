package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.SystemConfiguration;
import com.chamod.ticketingbackend.model.Vendor;
import com.chamod.ticketingbackend.repository.VendorRepository;
import com.chamod.ticketingbackend.service.ConfigService;
import com.chamod.ticketingbackend.service.TicketPoolService;
import com.chamod.ticketingbackend.service.VendorRunner;
import com.chamod.ticketingbackend.service.VendorService;
//import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final List<VendorRunner> vendorRunners = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(VendorServiceImpl.class);

//    @PostConstruct
//    public void initializeVendors() {
//        syncVendorsWithDatabase();
//    }

    @Override
    public void addVendor() {
        SystemConfiguration systemConfiguration = configService.getConfiguration();

        Vendor vendor = new Vendor();
        vendor.setTicketsPerRelease(systemConfiguration.getTicketReleaseRate());
        vendor.setReleaseInterval(1); // Default interval of 1 second
        vendor.setCreatedAt(LocalDateTime.now());

        // save vendor to database
        vendorRepository.save(vendor);

        // add vendor to list
        vendors.add(vendor);

        VendorRunner vendorRunner = new VendorRunner(vendor, ticketPoolService);
        Thread vendorThread = new Thread(vendorRunner);
        vendorThreads.add(vendorThread);
        vendorRunners.add(vendorRunner);

        logger.info("Vendor-{} added.", vendorRunner.getVendorId());
    }

    @Override
    public void removeVendor() {
//        syncVendorsWithDatabase();

        if (!vendors.isEmpty() && vendorRepository != null) {
            int lastIndex = (vendors.size()-1);
            // Remove vendor thread
            Thread threadToRemove = vendorThreads.get(lastIndex);

            threadToRemove.interrupt(); // Interrupt the thread

            vendorThreads.remove(threadToRemove);
            vendorRunners.remove(vendorRunners.get(lastIndex));

            // Remove vendor from database
            Vendor vendorToRemove = vendors.get(lastIndex);
            vendorRepository.delete(vendorToRemove);
            vendors.remove(vendorToRemove);
            VendorRunner.removeVendorCount();

            logger.info("Vendor-{} removed.", vendors.size()+1);
        } else {
            logger.warn("No vendors to remove.");
        }
    }

//    private void syncVendorsWithDatabase() {
//        vendors.clear();
//        vendors.addAll(vendorRepository.findAll());
//        for (Vendor vendor : vendors) {
//            Thread vendorThread = new Thread(new VendorRunner(vendor, ticketPoolService));
//            vendorThreads.add(vendorThread);
//        }
//        logger.info("Synchronized vendors with database. Total vendors: {}", vendors.size());
//    }

    @Override
    public void startVendors() {
        if (vendorThreads.isEmpty()) {
            logger.warn("No vendors to start.");
            return;
        }
        for (int i = 0; i < vendorThreads.size(); i++) {
            Thread vendorThread = vendorThreads.get(i);
            VendorRunner vendorRunner = vendorRunners.get(i);

            if (!vendorThread.isAlive()) {
                vendorThread = new Thread(vendorRunner);
                vendorThreads.set(i, vendorThread);
                vendorThread.start();
                logger.info("Vendor-{} started.", vendorRunner.getVendorId());
            } else {
                logger.warn("Vendor-{} already started.", vendorRunner.getVendorId());
            }
        }
    }

    @Override
    public void pauseVendors() {
        if (vendorThreads.isEmpty()) {
            logger.warn("No vendors to pause.");
            return;
        }

        for (VendorRunner vendorRunner : vendorRunners) {
            vendorRunner.pause();
            logger.info("Vendor-{} paused.", vendorRunner.getVendorId());
        }
    }

    @Override
    public void stopVendors() {
        if (vendorThreads.isEmpty()) {
            logger.warn("No vendors to stop.");
            return;
        }
        for (int i = 0; i < vendorThreads.size(); i++) {
            VendorRunner vendorRunner = new VendorRunner(vendors.get(i), ticketPoolService);
            vendorRunner.stop();
            Thread vendorThread = vendorThreads.get(i);
            vendorThread.interrupt();
        }
        vendors.clear();
        vendorThreads.clear();
        vendorRunners.clear();
        VendorRunner.resetVendorCount();
        configService.loadConfiguration();
        logger.info("All vendors stopped.");
    }

    @Override
    public void resumeVendors() {
        if (vendorThreads.isEmpty()) {
            logger.warn("No vendors to resume.");
            return;
        }

        for (VendorRunner vendorRunner : vendorRunners) {
            vendorRunner.resume();
            logger.info("Vendor-{} resumed.", vendorRunner.getVendorId());
        }
    }
}
