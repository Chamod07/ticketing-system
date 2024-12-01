package com.chamod.ticketingsystembackend.controller;

import com.chamod.ticketingsystembackend.dto.request.VendorSaveRequestDto;
import com.chamod.ticketingsystembackend.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/v1/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping(path = "/vendor-save")
    public String saveVendor(@RequestBody VendorSaveRequestDto vendorSaveRequestDto) {
        return vendorService.save(vendorSaveRequestDto);
    }
}
