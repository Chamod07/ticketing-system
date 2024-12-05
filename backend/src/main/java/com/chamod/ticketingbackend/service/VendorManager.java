package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.TicketPool;
import com.chamod.ticketingbackend.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendorManager {
    @Autowired
    private TicketPool ticketPool;

    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Vendor> vendors = new ArrayList<>();

    public void registerVendor(Long vendorId, int retrievalRate, int retrievalInterval) {
        VendorRunner vendorRunner = new VendorRunner();
        vendors.add(vendorRunner);
    }

    public void startVendors() {
        for (Vendor vendor : vendors) {
            Thread thread = new Thread(vendor);
            vendorThreads.add(thread);
            thread.start();
        }
    }

    public void stopVendors() {
        for (VendorRunner vendor : vendors) {
            vendor.stop(); // Signal vendors to stop
        }
        for (Thread thread : vendorThreads) {
            try {
                thread.join(); // Wait for threads to terminate
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        vendorThreads.clear();
    }
}
