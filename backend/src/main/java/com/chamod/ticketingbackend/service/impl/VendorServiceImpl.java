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
        // Test added vendors
        for (Vendor v : vendors) {
            System.out.println("Vendor " + v.getVendorId());
        }

        Thread vendorThread = new Thread(new VendorRunner(vendor, ticketPoolService));
        vendorThreads.add(vendorThread);
        // Test vendor threads
        for (Thread t : vendorThreads) {
            System.out.println("Vendor" + t.getName());
        }

        logger.info("Vendor-{} added.", vendor.getVendorId());
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

    @Override
    public void startVendors() {
        if (vendorThreads.isEmpty()) {
            logger.warn("No vendors to start.");
            return;
        }
        for (int i = 0; i < vendorThreads.size(); i++) {
            Thread vendorThread = vendorThreads.get(i);
            VendorRunner vendorRunner = new VendorRunner(vendors.get(i), ticketPoolService);

            if (!vendorThread.isAlive()) {
                vendorThread = new Thread(vendorRunner);
                vendorThreads.set(i, vendorThread);
                vendorThread.start();
                logger.info("Vendor-{} started.", vendors.get(i).getVendorId());
            } else {
                logger.warn("Vendor-{} already started.", vendors.get(i).getVendorId());
            }
        }
    }

    @Override
    public void pauseVendors() {
    }

    @Override
    public void stopVendors() {
    }

    @Override
    public void resumeVendors() {
    }
}
