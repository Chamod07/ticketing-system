package com.chamod.ticketingsystembackend.service;

import com.chamod.ticketingsystembackend.dto.request.CustomerSaveRequestDTO;

public interface CustomerService {

    String saveCustomer(CustomerSaveRequestDTO customerSaveRequestDTO);

    String updateCustomer(CustomerSaveRequestDTO customerSaveRequestDTO);
}
