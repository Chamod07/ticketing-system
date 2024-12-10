package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.Ticket;
import com.chamod.ticketingbackend.service.TicketPoolService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketPoolServiceImpl implements TicketPoolService {

    private final LinkedList<Ticket> tickets = new LinkedList<>();
    private int maxCapacity;
    private int ticketsPurchased;
    private int ticketsReleased;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final Logger logger = LogManager.getLogger(TicketPoolServiceImpl.class);

    @Override
    public void configure(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;

        try {
            tickets.clear();
            for (int i = 0; i < totalTickets; i++) {
                tickets.add(new Ticket()); // Initialize the tickets
            }
            ticketsPurchased = 0;
            ticketsReleased = 0;
            logger.info("Ticket pool configured with max capacity-{} and total tickets-{}", maxCapacity, totalTickets);
        } catch (Exception e) {
            logger.error("Error configuring ticket pool.", e);
        }
    }

    @Override
    public void addTickets(int ticketCount, int vendorId) {
        lock.lock();
        try {
            while (tickets.size() + ticketCount > maxCapacity) {
                logger.warn("[Vendor-{}] Pool is full. Waiting to add tickets (Pool size-{}).", vendorId, tickets.size());
                notFull.await();
            }

            for (int i = 0; i < ticketCount; i++) {
                tickets.add(new Ticket());
            }

            ticketsReleased += ticketCount; // the number of tickets added

            logger.info("[Vendor-{}] Added {} tickets. (Pool size-{})", vendorId, ticketCount, tickets.size());
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[Vendor-{}] Ticket addition interrupted.", vendorId);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void removeTickets(int ticketCount, int customerId) {
        lock.lock();
        try {
            while (tickets.size() < ticketCount) {
                logger.warn("[Customer-{}] Not enough tickets available. Waiting to purchase tickets (Pool size-{}).", customerId, tickets.size());
                notEmpty.await();
            }

            for (int i = 0; i < ticketCount; i++) {
                tickets.remove(0);
            }

            ticketsPurchased += ticketCount; // update the number of tickets purchased

            logger.info("[Customer-{}] Purchased {} tickets. (Pool size-{})", customerId, ticketCount, tickets.size());
            notFull.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[Customer-{}] Ticket purchase interrupted.", customerId);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getAvailableTickets() {
        return tickets.size();
    }

    @Override
    public int getPurchasedTickets() {
        return ticketsPurchased;
    }

    @Override
    public int getReleasedTickets() {
        return ticketsReleased;
    }

}
