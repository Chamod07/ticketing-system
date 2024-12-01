package com.chamod.ticketingsystembackend.service.impl;

import com.chamod.ticketingsystembackend.dto.request.CustomerSaveRequestDTO;
import com.chamod.ticketingsystembackend.entity.Customer;
import com.chamod.ticketingsystembackend.repository.CustomerRepository;
import com.chamod.ticketingsystembackend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public String saveCustomer(CustomerSaveRequestDTO customerSaveRequestDTO) {

        Customer customer = new Customer(
                customerSaveRequestDTO.getCustomerId(),
                customerSaveRequestDTO.getRetrievalRate(),
                customerSaveRequestDTO.getRetrievalInterval()
        );

        customerRepository.save(customer); // save method comes from the super class JPARepository

        return "Customer saved successfully";
    }
}