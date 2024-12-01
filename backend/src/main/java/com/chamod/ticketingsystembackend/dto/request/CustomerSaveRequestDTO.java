package com.chamod.ticketingsystembackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerSaveRequestDTO {

    private String customerId;
    private int retrievalRate;
    private int retrievalInterval;
}
