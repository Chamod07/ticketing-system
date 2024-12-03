package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.entity.Ticket;
import com.chamod.ticketingbackend.service.TicketPoolService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketPoolServiceImpl implements TicketPoolService {

    private List<Ticket> tickets;
    private final ReentrantLock lock = new ReentrantLock();

    public TicketPoolServiceImpl() {
        this.tickets = Collections.synchronizedList(new LinkedList<>());
    }

    @Override
    public void addTickets(int ticketCount) {

    }

    @Override
    public void removeTickets(int ticketCount) {

    }
}
