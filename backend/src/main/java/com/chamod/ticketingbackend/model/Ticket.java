package com.chamod.ticketingbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a ticket in the ticketing system.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ticket {

    /**
     * The unique identifier of the ticket.
     */
    private Long id;

    /**
     * The type of the ticket (e.g., VIP, Regular).
     */
    private String type;

    /**
     * The price of the ticket.
     */
    private Double price;
}