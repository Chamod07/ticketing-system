package com.chamod.ticketingsystembackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorSaveRequestDto {

    private int vendorId;
    private int retrievalRate;
    private int retrievalInterval;
}
