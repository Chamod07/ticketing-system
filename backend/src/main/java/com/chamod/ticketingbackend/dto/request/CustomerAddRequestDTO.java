package com.chamod.ticketingbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerAddRequestDTO {

    private Long id;
    private int retrievalRate;
    private int retrievalInterval;
    private Boolean priority;
    private Integer ticketsPurchased;
}
