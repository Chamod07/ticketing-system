package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.dto.request.VendorAddRequestDto;

public interface VendorService {

    String save(VendorAddRequestDto vendorAddRequestDto);

    void addTickets(int ticketCount, int vendorId);

}
