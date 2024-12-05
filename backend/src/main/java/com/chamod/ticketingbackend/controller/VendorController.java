package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.dto.request.VendorAddRequestDto;
import com.chamod.ticketingbackend.service.VendorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/v1/vendor")
public class VendorController {

    @Autowired
    private VendorManager vendorManager;

    @PostMapping(path = "/vendor-save")
    public String saveVendor(@RequestBody VendorAddRequestDto vendorAddRequestDto) {
        vendorManager.registerVendor(
                vendorAddRequestDto.getVendorId(),
                vendorAddRequestDto.getTicketsPerRelease(),
                vendorAddRequestDto.getReleaseInterval()
                );
        return "Vendor saved successfully.";
    }

    @PostMapping(path = "/vendor-start")
    public String startVendors() {
        vendorManager.startVendors();
        return "Vendors started successfully.";
    }

    @PostMapping(path = "/vendor-stop")
    public String stopVendors() {
        vendorManager.stopVendors();
        return "Vendors stopped successfully.";
    }
}
