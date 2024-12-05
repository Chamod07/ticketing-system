package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.dto.request.VendorAddRequestDto;
import com.chamod.ticketingbackend.model.Vendor;
import com.chamod.ticketingbackend.repository.VendorRepository;
import com.chamod.ticketingbackend.service.TicketPoolService;
import com.chamod.ticketingbackend.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private TicketPoolService ticketPoolService;

    @Override
    public String save(VendorAddRequestDto vendorAddRequestDto) {

        Vendor vendor = new Vendor(
                vendorAddRequestDto.getVendorId(),
                vendorAddRequestDto.getTicketsPerRelease(),
                vendorAddRequestDto.getReleaseInterval()
        );

        if (!vendorRepository.existsById(Math.toIntExact(vendor.getVendorId()))) {
            vendorRepository.save(vendor);
            return "Vendor saved successfully";
        } else {
            return "Vendor already exists";
        }
    }

    @Override
    public void addTickets(int ticketCount, int vendorId) {
        ticketPoolService.addTickets(ticketCount);
    }
}
