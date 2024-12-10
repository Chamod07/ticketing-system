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
    public int addCustomer() {
        customerService.addCustomer();
        return customerService.getCustomerCount();
    }

    @DeleteMapping(path = "/remove")
    public int removeCustomer() {
        customerService.removeCustomer();
        return customerService.getCustomerCount();
    }

    @GetMapping(path = "/count")
    public int getCustomerCount() {
        return customerService.getCustomerCount();
    }

    @PostMapping(path = "/start")
    public String startCustomer() {
        customerService.startCustomers();
        return "Customer started successfully.";
    }

    @PostMapping(path = "/pause")
    public String pauseCustomer() {
        customerService.pauseCustomers();
        return "Customer paused successfully.";
    }

    @PostMapping(path = "/stop")
    public String stopCustomer() {
        customerService.stopCustomers();
        return "Customer stopped successfully.";
    }

    @PostMapping(path = "/resume")
    public String resumeCustomer() {
        customerService.resumeCustomers();
        return "Customer resumed successfully.";
    }

}
