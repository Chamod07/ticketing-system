package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/system")
public class SystemController {

    @Autowired
    private VendorService vendorService;

//    @PostMapping(path = "/start")
//    public String startSystem() {
//        vendorService.startVendors();
//        return "Successfully started system.";
//    }
//
//    @PostMapping(path = "/pause")
//    public String pauseSystem() {
//        vendorService.pauseVendors();
//        return "Successfully paused system.";
//    }
//
//    @PostMapping(path = "/resume")
//    public String resumeSystem() {
//        vendorService.pauseVendors();
//        return "Successfully resumed system.";
//    }
}
