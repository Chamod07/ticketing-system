package com.chamod.ticketingsystembackend.service;

import com.chamod.ticketingsystembackend.dto.request.VendorSaveRequestDto;

public interface VendorService {

    String save(VendorSaveRequestDto vendorSaveRequestDto);
}
