package com.chamod.ticketingbackend.service;

public interface VendorService {

    void addVendor();

    void removeVendor();

    void startVendors();

    void pauseVendors();

    void stopVendors();

    void resumeVendors();
}
