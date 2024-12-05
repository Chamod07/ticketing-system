package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.Ticket;
import com.chamod.ticketingbackend.service.TicketPoolService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketPoolServiceImpl implements TicketPoolService {

    private final List<Ticket> tickets;
    private int maxCapacity = 100;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull;
    private final Condition notEmpty;
    private static final Logger logger = LogManager.getLogger(TicketPoolServiceImpl.class);

    public TicketPoolServiceImpl() {
        this.maxCapacity = maxCapacity;
        this.tickets = Collections.synchronizedList(new LinkedList<>());
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();
    }

    @Override
    public void addTickets(int ticketCount) {
        lock.lock();
        try {
            while (tickets.size() + ticketCount > maxCapacity) {
                logger.warn("Cannot add tickets. Not enough space.");
                notFull.await();
            }
            for (int i = 0; i < ticketCount; i++) {
                tickets.add(new Ticket());
            }
            logger.info("Added {} tickets. (Pool size: {})", ticketCount, tickets.size());
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            logger.error("Ticket addition interrupted.", e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void removeTickets(int ticketCount) {
        lock.lock();
        try {
            while (tickets.size() < ticketCount) {
                logger.warn("Not enough tickets available. Waiting for tickets to be added.");
                notEmpty.await();
            }
            for (int i = 0; i < ticketCount; i++) {
                tickets.remove(0);
            }
            logger.info("Purchased {} tickets. (Pool size: {})", ticketCount, tickets.size());
            notFull.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Ticket removal interrupted.", e);
        } finally {
            lock.unlock();
        }
    }
}
