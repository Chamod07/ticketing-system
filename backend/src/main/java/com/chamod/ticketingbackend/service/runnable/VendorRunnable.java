package com.chamod.ticketingbackend.service.runnable;

import com.chamod.ticketingbackend.model.Vendor;
import com.chamod.ticketingbackend.service.TicketPoolService;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Runnable implementation for managing vendor operations.
 */
public class VendorRunnable implements Runnable {
    @Getter
    private final Vendor vendor;
    @Getter
    private final int vendorId;
    private static int vendorCount = 1;
    private final TicketPoolService ticketPool;
    private volatile boolean paused = false;

    private static final Logger logger = LogManager.getLogger(VendorRunnable.class);

    /**
     * Constructor for VendorRunnable.
     *
     * @param vendor the vendor associated with this runnable
     * @param ticketPool the ticket pool service to manage tickets
     */
    public VendorRunnable(Vendor vendor, TicketPoolService ticketPool) {
        this.vendor = vendor;
        this.ticketPool = ticketPool;
        this.vendorId = vendorCount++;
    }

    /**
     * Resets the vendor count to 1.
     */
    public static void resetVendorCount() {
        vendorCount = 1;
    }

    /**
     * Decreases the vendor count by 1.
     */
    public static void removeVendorCount() {
        vendorCount--;
    }

    /**
     * Stops the vendor runnable.
     */
    public void stop() {
        this.paused = true;
    }

    /**
     * Pauses the vendor runnable.
     */
    public void pause() {
        this.paused = true;
    }

    /**
     * Resumes the vendor runnable.
     */
    public void resume() {
        synchronized (this) {
            paused = false;
            notify();
        }
    }

    /**
     * The main logic of the vendor runnable.
     * Adds tickets to the ticket pool at regular intervals.
     */
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
                Thread.sleep(vendor.getReleaseInterval() * 1000L);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[Vendor-{}] Interrupted during operation.", vendorId);
        }
    }
}
