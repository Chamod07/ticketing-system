package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.dto.request.VendorAddRequestDto;
import com.chamod.ticketingbackend.entity.Vendor;
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

//    @Autowired
//    private ModelMapper modelMapper;

    @Override
    public String save(VendorAddRequestDto vendorAddRequestDto) {

        Vendor vendor = new Vendor(
                vendorAddRequestDto.getId(),
                vendorAddRequestDto.getRetrievalRate(),
                vendorAddRequestDto.getRetrievalInterval(),
                vendorAddRequestDto.getTicketsReleased()
        );

        if (!vendorRepository.existsById(Math.toIntExact(vendor.getId()))) {
            vendorRepository.save(vendor);
            return "Vendor saved successfully";
        } else {
            return "Vendor already exists";
        }

//        Vendor vendor = modelMapper.map(vendorSaveRequestDto, Vendor.class);
//        vendorRepository.save(vendor);
    }

    @Override
    public void addTickets(int ticketCount, int vendorId) {
        ticketPoolService.addTickets(ticketCount);
    }
}
