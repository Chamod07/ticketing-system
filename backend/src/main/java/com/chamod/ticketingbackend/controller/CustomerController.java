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
        boolean isVip = false;
        customerService.addCustomer(isVip);
        return customerService.getCustomerCount();
    }

    @PostMapping(path = "/add-vip")
    public int addVipCustomer() {
        boolean isVip = true;
        customerService.addCustomer(isVip);
        return customerService.getVipCustomerCount();
    }

    @DeleteMapping(path = "/remove")
    public int removeCustomer() {
        boolean isVip = false;
        customerService.removeCustomer(isVip);
        return customerService.getCustomerCount();
    }

    @DeleteMapping(path = "/remove-vip")
    public int removeVipCustomer() {
        boolean isVip = true;
        customerService.removeCustomer(isVip);
        return customerService.getVipCustomerCount();
    }

    @GetMapping(path = "/count")
    public int getCustomerCount() {
        return customerService.getCustomerCount();
    }

    @GetMapping(path = "/count-vip")
    public int getVipCustomerCount() {
        return customerService.getVipCustomerCount();
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
