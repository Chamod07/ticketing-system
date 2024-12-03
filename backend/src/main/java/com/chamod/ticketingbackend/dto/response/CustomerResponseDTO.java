package com.chamod.ticketingbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerResponseDTO {

    private int retrievalRate;
    private int retrievalInterval;
    private Boolean priority;
    private Integer ticketsPurchased;
}
