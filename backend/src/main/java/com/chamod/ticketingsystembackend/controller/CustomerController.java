package com.chamod.ticketingsystembackend.controller;

import com.chamod.ticketingsystembackend.dto.request.CustomerSaveRequestDTO;
import com.chamod.ticketingsystembackend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/save-customer")
    public String saveCustomer(@RequestBody CustomerSaveRequestDTO customerSaveRequestDTO) {

        return customerService.saveCustomer(customerSaveRequestDTO);
    }

    @PutMapping(path = "/update-customer")
    public String updateCustomer(@RequestBody CustomerSaveRequestDTO customerSaveRequestDTO) {

        return customerService.updateCustomer(customerSaveRequestDTO);
    }

}
