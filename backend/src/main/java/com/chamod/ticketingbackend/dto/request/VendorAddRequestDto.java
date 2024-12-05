package com.chamod.ticketingbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorAddRequestDto {

    private Long vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;
}
