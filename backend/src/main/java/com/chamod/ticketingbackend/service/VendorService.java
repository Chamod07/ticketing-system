package com.chamod.ticketingbackend.service;

/**
 * Service interface for managing vendors.
 */
public interface VendorService {

    /**
     * Adds a new vendor.
     */
    void addVendor();

    /**
     * Removes an existing vendor.
     */
    void removeVendor();

    /**
     * Starts all vendors.
     */
    void startVendors();

    /**
     * Pauses all vendors.
     */
    void pauseVendors();

    /**
     * Stops all vendors.
     */
    void stopVendors();

    /**
     * Resumes all vendors.
     */
    void resumeVendors();

    /**
     * Returns the count of vendors.
     *
     * @return the number of vendors
     */
    int vendorCount();
}