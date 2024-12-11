package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing vendors.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/v1/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    /**
     * Adds a new vendor.
     *
     * @return the total number of vendors after the addition.
     */
    @PostMapping(path = "/add")
    public int addVendor() {
        vendorService.addVendor();
        return vendorService.vendorCount();
    }

    /**
     * Removes a vendor.
     *
     * @return the total number of vendors after the removal.
     */
    @DeleteMapping(path = "/remove")
    public int removeVendor() {
        vendorService.removeVendor();
        return vendorService.vendorCount();
    }

    /**
     * Gets the total number of vendors.
     *
     * @return the total number of vendors.
     */
    @GetMapping(path = "/count")
    public int countVendor() {
        return vendorService.vendorCount();
    }

    /**
     * Starts all vendors.
     *
     * @return a message indicating the vendors have been started.
     */
    @PostMapping(path = "/start")
    public String startVendors() {
        vendorService.startVendors();
        return "Vendors started successfully.";
    }

    /**
     * Pauses all vendors.
     *
     * @return a message indicating the vendors have been paused.
     */
    @PostMapping(path = "/pause")
    public String pauseVendors() {
        vendorService.pauseVendors();
        return "Vendors paused successfully.";
    }

    /**
     * Stops all vendors.
     *
     * @return a message indicating the vendors have been stopped.
     */
    @PostMapping(path = "/stop")
    public String stopVendors() {
        vendorService.stopVendors();
        return "Vendors stopped successfully.";
    }

    /**
     * Resumes all vendors.
     *
     * @return a message indicating the vendors have been resumed.
     */
    @PostMapping(path = "/resume")
    public String resumeVendors() {
        vendorService.resumeVendors();
        return "Vendors resumed successfully.";
    }
}