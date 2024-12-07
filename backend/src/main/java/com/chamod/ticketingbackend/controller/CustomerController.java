package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/add")
    public String saveVendor() {
        customerService.addCustomer();
        return "Customer added successfully.";
    }

    @DeleteMapping(path = "/remove")
    public String removeVendor() {
        customerService.removeCustomer();
        return "Customer removed successfully.";
    }

    @PostMapping(path = "/start")
    public String startVendors() {
        customerService.startCustomers();
        return "Customer started successfully.";
    }

    @PostMapping(path = "/pause")
    public String pauseVendors() {
        customerService.pauseCustomers();
        return "Customer paused successfully.";
    }

    @PostMapping(path = "/stop")
    public String stopVendors() {
        customerService.stopCustomers();
        return "Customer stopped successfully.";
    }

    @PostMapping(path = "/resume")
    public String resumeVendors() {
        customerService.resumeCustomers();
        return "Customer resumed successfully.";
    }

}
