package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.SystemConfiguration;
import com.chamod.ticketingbackend.model.Vendor;
import com.chamod.ticketingbackend.repository.VendorRepository;
import com.chamod.ticketingbackend.service.*;
import com.chamod.ticketingbackend.service.runnable.VendorRunnable;

//import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VendorServiceImpl implements VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private ConfigService configService;

    @Lazy
    @Autowired
    private SystemService systemService;

    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Vendor> vendors = new ArrayList<>();
    private final List<VendorRunnable> vendorRunnables = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(VendorServiceImpl.class);

//    @PostConstruct
//    public void initializeVendors() {
//        syncVendorsWithDatabase();
//    }

    @Override
    public void addVendor() {
        SystemConfiguration systemConfiguration = configService.getConfiguration();

        if (systemConfiguration == null) {
            logger.warn("System configuration is not initialized. Cannot add new vendor.");
        } else {
            Vendor vendor = new Vendor();
            vendor.setTicketsPerRelease(systemConfiguration.getTicketReleaseRate());
            vendor.setReleaseInterval(1); // Default interval of 1 second
            vendor.setTimestamp(LocalDateTime.now());

            // save vendor to database
            vendorRepository.save(vendor);

            // add vendor to list
            vendors.add(vendor);

            VendorRunnable vendorRunnable = new VendorRunnable(vendor, ticketPoolService);
            Thread vendorThread = new Thread(vendorRunnable);

            vendorThreads.add(vendorThread);
            vendorRunnables.add(vendorRunnable);

            // start thread if system is running
            if (Objects.equals(systemService.getState(), "Running")) {
                vendorThread.start();
                logger.info("Vendor-{} added and started as the system is running.", vendorRunnable.getVendorId());
            } else {
                logger.info("Vendor-{} added.", vendorRunnable.getVendorId());
            }
        }
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
            vendorRunnables.remove(vendorRunnables.get(lastIndex));

            // Remove vendor from database
            Vendor vendorToRemove = vendors.get(lastIndex);
            vendorRepository.delete(vendorToRemove);

            vendors.remove(vendorToRemove);
            VendorRunnable.removeVendorCount();

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
            VendorRunnable vendorRunnable = vendorRunnables.get(i);

            if (!vendorThread.isAlive()) {
                vendorThread = new Thread(vendorRunnable);
                vendorThreads.set(i, vendorThread);
                vendorThread.start();
                logger.info("Vendor-{} started.", vendorRunnable.getVendorId());
            } else {
                logger.warn("Vendor-{} already started.", vendorRunnable.getVendorId());
            }
        }
    }

    @Override
    public void pauseVendors() {
        if (vendorThreads.isEmpty()) {
            logger.warn("No vendors to pause.");
            return;
        }

        for (VendorRunnable vendorRunnable : vendorRunnables) {
            vendorRunnable.pause();
            logger.info("Vendor-{} paused.", vendorRunnable.getVendorId());
        }
    }

    @Override
    public void stopVendors() {
        if (vendorThreads.isEmpty()) {
            logger.warn("No vendors to stop.");
            return;
        }

        for (int i = 0; i < vendorThreads.size(); i++) {
            VendorRunnable vendorRunnable = new VendorRunnable(vendors.get(i), ticketPoolService);
            vendorRunnable.stop();
            Thread vendorThread = vendorThreads.get(i);
            vendorThread.interrupt();
        }
        vendors.clear();
        vendorThreads.clear();
        vendorRunnables.clear();
        VendorRunnable.resetVendorCount();
        configService.loadConfiguration();
        logger.info("All vendors stopped.");
    }

    @Override
    public void resumeVendors() {
        if (vendorThreads.isEmpty()) {
            logger.warn("No vendors to resume.");
            return;
        }

        for (VendorRunnable vendorRunnable : vendorRunnables) {
            vendorRunnable.resume();
            logger.info("Vendor-{} resumed.", vendorRunnable.getVendorId());
        }

        // start vendors added while paused
        for (int i = 0; i < vendorThreads.size(); i++) {
            Thread vendorThread = vendorThreads.get(i);
            if (!vendorThread.isAlive()) {
                vendorThread.start();
                logger.info("Vendor-{} started during system resume.", vendorRunnables.get(i).getVendorId());
            }
        }
    }

    @Override
    public int vendorCount() {
        return vendors.size();
    }
}
