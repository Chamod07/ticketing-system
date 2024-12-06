package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.Vendor;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VendorRunner implements Runnable{
    @Getter
    private final Vendor vendor;
    @Getter
    private final int vendorId;
    private static int vendorCount = 1;
    private final TicketPoolService ticketPool;
    private volatile boolean paused = false;

    private static final Logger logger = LogManager.getLogger(VendorRunner.class);

    public VendorRunner(Vendor vendor, TicketPoolService ticketPool) {
        this.vendor = vendor;
        this.ticketPool = ticketPool;
        this.vendorId = vendorCount++;
    }

    public static void resetVendorCount() {
        vendorCount = 1;
    }

    public static void removeVendorCount() {
        vendorCount--;
    }

    public void stop() {
        this.paused = true;
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        synchronized (this) {
            paused = false;
            notify();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    while (paused) {
                        wait();
                    }
                }

                if (Thread.currentThread().isInterrupted()) {
                    logger.info("[Vendor-{}] Thread interrupted.", vendorId);
                    return;
                }
                ticketPool.addTickets(vendor.getTicketsPerRelease(), vendorId);
                Thread.sleep(vendor.getReleaseInterval()*1000L);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[Vendor-{}] Interrupted during operation.", vendorId);
        }
    }
}
