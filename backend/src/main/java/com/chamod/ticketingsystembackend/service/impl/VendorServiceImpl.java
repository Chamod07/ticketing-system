package com.chamod.ticketingsystembackend.service.impl;

import com.chamod.ticketingsystembackend.dto.request.VendorSaveRequestDto;
import com.chamod.ticketingsystembackend.entity.Vendor;
import com.chamod.ticketingsystembackend.repository.VendorRepository;
import com.chamod.ticketingsystembackend.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

//    @Autowired
//    private ModelMapper modelMapper;

    @Override
    public String save(VendorSaveRequestDto vendorSaveRequestDto) {

        Vendor vendor = new Vendor(
                vendorSaveRequestDto.getVendorId(),
                vendorSaveRequestDto.getRetrievalRate(),
                vendorSaveRequestDto.getRetrievalInterval()
        );

        if (!vendorRepository.existsById(vendor.getVendorId())) {
            vendorRepository.save(vendor);
            return "Vendor saved successfully";
        } else {
            return "Vendor already exists";
        }

//        Vendor vendor = modelMapper.map(vendorSaveRequestDto, Vendor.class);
//        vendorRepository.save(vendor);
    }
}
