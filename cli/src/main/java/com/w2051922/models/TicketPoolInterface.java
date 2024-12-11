package com.w2051922.models;

/**
 * Interface representing a pool of tickets.
 */
public interface TicketPoolInterface {

    /**
     * Adds a specified number of tickets to the pool for a given vendor.
     *
     * @param ticketCount the number of tickets to add
     * @param vendorId the ID of the vendor
     */
    void addTicket(int ticketCount, String vendorId);

    /**
     * Removes a specified number of tickets from the pool for a given customer.
     *
     * @param ticketCount the number of tickets to remove
     * @param customerId the ID of the customer
     */
    void removeTicket(int ticketCount, String customerId);
}