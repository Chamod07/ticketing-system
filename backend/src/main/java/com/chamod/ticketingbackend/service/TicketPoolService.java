package com.chamod.ticketingbackend.service;

/**
 * Service interface for managing the ticket pool.
 */
public interface TicketPoolService {

    /**
     * Configures the ticket pool with the specified maximum capacity and total tickets.
     *
     * @param maxCapacity the maximum capacity of the ticket pool
     * @param totalTickets the total number of tickets available
     */
    void configure(int maxCapacity, int totalTickets);

    /**
     * Adds tickets to the pool from a specific vendor.
     *
     * @param ticketCount the number of tickets to add
     * @param vendorId the ID of the vendor providing the tickets
     */
    void addTickets(int ticketCount, int vendorId);

    /**
     * Removes tickets from the pool for a specific customer.
     *
     * @param ticketCount the number of tickets to remove
     * @param customerId the ID of the customer purchasing the tickets
     * @param isVip whether the customer is a VIP
     */
    void removeTickets(int ticketCount, int customerId, boolean isVip);

    /**
     * Gets the number of available tickets in the pool.
     *
     * @return the number of available tickets
     */
    int getAvailableTickets();

    /**
     * Gets the number of tickets that have been purchased.
     *
     * @return the number of purchased tickets
     */
    int getPurchasedTickets();

    /**
     * Gets the number of tickets that have been released back into the pool.
     *
     * @return the number of released tickets
     */
    int getReleasedTickets();

    /**
     * Gets the maximum capacity of the ticket pool.
     *
     * @return the maximum capacity
     */
    int getMaxCapacity();
}