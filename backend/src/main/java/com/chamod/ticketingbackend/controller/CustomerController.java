package com.chamod.ticketingbackend.controller;

import com.chamod.ticketingbackend.dto.request.CustomerAddRequestDTO;
import com.chamod.ticketingbackend.dto.response.CustomerResponseDTO;
import com.chamod.ticketingbackend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/save-customer")
    public String saveCustomer(@RequestBody CustomerAddRequestDTO customerAddRequestDTO) {

        return customerService.saveCustomer(customerAddRequestDTO);
    }

    @PutMapping(path = "/update-customer")
    public String updateCustomer(@RequestBody CustomerAddRequestDTO customerAddRequestDTO) {

        return customerService.updateCustomer(customerAddRequestDTO);
    }

    @GetMapping(
            path = "/get-customer",
            params = "id"
    )
    public CustomerResponseDTO getCustomer(@RequestParam(value = "id") int customerId) {

        return customerService.getCustomer(customerId);
    }

}
