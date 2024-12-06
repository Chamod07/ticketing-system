package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/v1/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping(path = "/add")
    public String saveVendor() {
        vendorService.addVendor();
        return "Vendor added successfully.";
    }

    @DeleteMapping(path = "/remove")
    public String removeVendor() {
        vendorService.removeVendor();
        return "Vendor removed successfully.";
    }

    @PostMapping(path = "/start")
    public String startVendors() {
        vendorService.startVendors();
        return "Vendors started successfully.";
    }

    @PostMapping(path = "/pause")
    public String pauseVendors() {
        vendorService.pauseVendors();
        return "Vendors paused successfully.";
    }

    @PostMapping(path = "/stop")
    public String stopVendors() {
        vendorService.stopVendors();
        return "Vendors stopped successfully.";
    }

    @PostMapping(path = "/resume")
    public String resumeVendors() {
        vendorService.resumeVendors();
        return "Vendors resumed successfully.";
    }
}
