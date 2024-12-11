package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing customers.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Adds a regular customer.
     *
     * @return the total number of customers.
     */
    @PostMapping(path = "/add")
    public int addCustomer() {
        boolean isVip = false;
        customerService.addCustomer(isVip);
        return customerService.getCustomerCount();
    }

    /**
     * Adds a VIP customer.
     *
     * @return the total number of VIP customers.
     */
    @PostMapping(path = "/add-vip")
    public int addVipCustomer() {
        boolean isVip = true;
        customerService.addCustomer(isVip);
        return customerService.getVipCustomerCount();
    }

    /**
     * Removes a regular customer.
     *
     * @return the total number of customers.
     */
    @DeleteMapping(path = "/remove")
    public int removeCustomer() {
        boolean isVip = false;
        customerService.removeCustomer(isVip);
        return customerService.getCustomerCount();
    }

    /**
     * Removes a VIP customer.
     *
     * @return the total number of VIP customers.
     */
    @DeleteMapping(path = "/remove-vip")
    public int removeVipCustomer() {
        boolean isVip = true;
        customerService.removeCustomer(isVip);
        return customerService.getVipCustomerCount();
    }

    /**
     * Gets the total number of customers.
     *
     * @return the total number of customers.
     */
    @GetMapping(path = "/count")
    public int getCustomerCount() {
        return customerService.getCustomerCount();
    }

    /**
     * Gets the total number of VIP customers.
     *
     * @return the total number of VIP customers.
     */
    @GetMapping(path = "/count-vip")
    public int getVipCustomerCount() {
        return customerService.getVipCustomerCount();
    }

    /**
     * Starts customer-related operations.
     *
     * @return a success message.
     */
    @PostMapping(path = "/start")
    public String startCustomer() {
        customerService.startCustomers();
        return "Customer started successfully.";
    }

    /**
     * Pauses customer-related operations.
     *
     * @return a success message.
     */
    @PostMapping(path = "/pause")
    public String pauseCustomer() {
        customerService.pauseCustomers();
        return "Customer paused successfully.";
    }

    /**
     * Stops customer-related operations.
     *
     * @return a success message.
     */
    @PostMapping(path = "/stop")
    public String stopCustomer() {
        customerService.stopCustomers();
        return "Customer stopped successfully.";
    }

    /**
     * Resumes customer-related operations.
     *
     * @return a success message.
     */
    @PostMapping(path = "/resume")
    public String resumeCustomer() {
        customerService.resumeCustomers();
        return "Customer resumed successfully.";
    }

}