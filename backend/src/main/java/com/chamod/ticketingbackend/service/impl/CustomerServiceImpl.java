package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.dto.request.CustomerAddRequestDTO;
import com.chamod.ticketingbackend.dto.response.CustomerResponseDTO;
import com.chamod.ticketingbackend.entity.Customer;
import com.chamod.ticketingbackend.repository.CustomerRepository;
import com.chamod.ticketingbackend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public String saveCustomer(CustomerAddRequestDTO customerAddRequestDTO) {

        Customer customer = new Customer(
                customerAddRequestDTO.getId(),
                customerAddRequestDTO.getPriority(),
                customerAddRequestDTO.getRetrievalRate(),
                customerAddRequestDTO.getRetrievalInterval(),
                customerAddRequestDTO.getTicketsPurchased()
        );

        customerRepository.save(customer); // save method comes from the super class JPARepository

        return "Customer saved successfully.";
    }

    @Override
    public String updateCustomer(CustomerAddRequestDTO customerAddRequestDTO) {

        if (customerRepository.existsById(String.valueOf(customerAddRequestDTO.getId()))) {
            Customer customer = customerRepository.getReferenceById(String.valueOf(customerAddRequestDTO.getId()));

            customer.setRetrievalRate(customerAddRequestDTO.getRetrievalRate());
            customer.setRetrievalInterval(customerAddRequestDTO.getRetrievalInterval());

            customerRepository.save(customer);

            return "Customer: " + customerAddRequestDTO.getId() + " updated successfully.";
        } else {
            return "Customer not found.";
        }

    }

    @Override
    public CustomerResponseDTO getCustomer(int customerId) {

        if (customerRepository.existsById(String.valueOf((customerId)))) {

            Customer customer = customerRepository.getReferenceById(String.valueOf(customerId));

            return new CustomerResponseDTO(
                    customer.getRetrievalRate(),
                    customer.getRetrievalInterval(),
                    customer.getPriority(),
                    customer.getTicketsPurchased()
            );
        } else {
            return null;
        }
        
    }
}
