package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.Vendor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VendorRunner implements Runnable{
    private final Vendor vendor;
    private final TicketPoolService ticketPool;

    private volatile boolean running = true;
    private volatile boolean paused = false;

    private static final Logger logger = LogManager.getLogger(VendorRunner.class);

    public VendorRunner(Vendor vendor, TicketPoolService ticketPool) {
        this.vendor = vendor;
        this.ticketPool = ticketPool;
    }

    public void stop() {
        this.running = false;
    }

    public void pause() {
        this.paused = true;
    }

    @Override
    public void run() {
        try {
            while (running) {
                synchronized (this) {
                    while (paused) {
                        wait();
                    }
                }

                ticketPool.addTickets(vendor.getTicketsPerRelease(), vendor.getVendorId());
                Thread.sleep(vendor.getReleaseInterval()*1000L);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[Vendor-{}] Interrupted during operation.", vendor.getVendorId());
        }
    }
}
