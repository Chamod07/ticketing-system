package com.chamod.ticketingbackend.service;

public interface TicketPoolService {

    void configure(int maxCapacity, int totalTickets);

    void addTickets(int ticketCount, int vendorId);

    void removeTickets(int ticketCount, int customerId);

    int getAvailableTickets();

    int getPurchasedTickets();

    int getReleasedTickets();

    int getMaxCapacity();
}
