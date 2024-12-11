package com.w2051922.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Vendor class represents a vendor that releases tickets at a specified rate.
 * It implements the Runnable interface to allow running in a separate thread.
 */
public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private String vendorID;
    private int releaseRate;
    private volatile boolean running = true;
    private static final Logger logger = LogManager.getLogger(Vendor.class.getName());

    /**
     * Constructs a Vendor with the specified ticket pool, vendor ID, and release rate.
     *
     * @param ticketPool the ticket pool to which tickets will be added
     * @param vendorID the ID of the vendor
     * @param releaseRate the rate at which tickets are released
     */
    public Vendor(TicketPool ticketPool, String vendorID, int releaseRate) {
        this.ticketPool = ticketPool;
        this.vendorID = vendorID;
        this.releaseRate = releaseRate;
    }

    /**
     * Gets the vendor ID.
     *
     * @return the vendor ID
     */
    public String getVendorID() {
        return vendorID;
    }

    /**
     * Sets the vendor ID.
     *
     * @param vendorID the new vendor ID
     */
    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    /**
     * Gets the release rate.
     *
     * @return the release rate
     */
    public int getReleaseRate() {
        return releaseRate;
    }

    /**
     * Sets the release rate.
     *
     * @param releaseRate the new release rate
     */
    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }

    /**
     * Runs the vendor, periodically adding tickets to the ticket pool.
     * The vendor runs until the running flag is set to false.
     */
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

    /**
     * Stops the vendor from running.
     */
    public void stop() {
        running = false;
    }

    /**
     * Returns a string representation of the vendor.
     *
     * @return a string representation of the vendor
     */
    @Override
    public String toString() {
        return vendorID + "\t" + releaseRate;
    }
}