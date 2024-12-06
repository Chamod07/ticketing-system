package com.chamod.ticketingbackend.service;

public interface TicketPoolService {

    void configure(int maxCapacity, int totalTickets);
    void addTickets(int ticketCount, Long vendorId);

}
