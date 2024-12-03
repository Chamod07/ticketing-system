package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.dto.request.CustomerAddRequestDTO;
import com.chamod.ticketingbackend.dto.response.CustomerResponseDTO;

public interface CustomerService {

    String saveCustomer(CustomerAddRequestDTO customerAddRequestDTO);

    String updateCustomer(CustomerAddRequestDTO customerAddRequestDTO);

    CustomerResponseDTO getCustomer(int customerId);
}
