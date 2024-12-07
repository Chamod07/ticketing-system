package com.w2051922.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private String vendorID;
    private int releaseRate;
    private volatile boolean running = true;
    private static final Logger logger = LogManager.getLogger(Vendor.class.getName());

    public Vendor(TicketPool ticketPool, String vendorID, int releaseRate) {
        this.ticketPool = ticketPool;
        this.vendorID = vendorID;
        this.releaseRate = releaseRate;
    }

    public String getVendorID() {
        return vendorID;
    }
    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }
    public int getReleaseRate() {
        return releaseRate;
    }
    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        try {
            while (running) {
                Thread.sleep(1000); // Wait for 1 second
                ticketPool.addTicket(releaseRate, vendorID); // Add tickets
            }
        } catch (InterruptedException e) {
            logger.error("[Vendor-{}] Interrupted.", vendorID);
            Thread.currentThread().interrupt();
        } finally {
            logger.info("[Vendor-{}] Vendor has been terminated.", vendorID);
        }
    }

    public void stop() {
        running = false;
    }

    @Override
    public String toString() {
        return vendorID + "\t" + releaseRate;
    }
}
