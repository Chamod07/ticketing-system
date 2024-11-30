package com.w2051922.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final List<Ticket> tickets;
    private final int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition notFull;
    private final Condition notEmpty;
    private static final Logger logger = LogManager.getLogger(TicketPool.class);

    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.tickets = Collections.synchronizedList(new LinkedList<>());
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();

        // initialise ticket pool with a set of tickets
        for (int i = 0; i < totalTickets; i++) {
            tickets.add(new Ticket());
        }
    }

    /**
     * Add tickets to the ticket pool
     * @param ticketCount number of tickets to sell
     * **/
    public void addTicket(int ticketCount, String vendorId) {
        lock.lock();
        try {
            // Make sure not to go beyond max capacity
            while (tickets.size() + ticketCount > maxCapacity) {
                logger.warn("[Vendor-{}] Cannot add tickets. Not enough space.", vendorId);
                notFull.await(); // Hold threads until tickets are available
            }
            for (int i = 0; i < ticketCount; i++) {
                tickets.add(new Ticket());
            }

            logger.info("[Vendor-{}] Added {} tickets. (Pool size: {})", vendorId, ticketCount, tickets.size());
            notEmpty.signalAll(); // Notify customers that tickets are available
        } catch (InterruptedException e) {
            logger.error("[Vendor-{}] Ticket addition interrupted.", vendorId, e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Remove tickets from ticket pool
     * @param ticketCount number of tickets to buy
     * **/
    public void removeTicket(int ticketCount, String customerId) {
        lock.lock();
        try {
            while (tickets.size() < ticketCount) {
                logger.warn("[Customer-{}] Not enough tickets available. Waiting for tickets to be added.", customerId);
                notEmpty.await(); // Wait until more tickets become available
            }

            // Make sure not to remove more tickets than available
            for (int i = 0; i < ticketCount; i++) {
                tickets.remove(0);
            }

            logger.info("[Customer-{}] Purchased {} tickets. (Pool size: {})", customerId, ticketCount, tickets.size());
            notFull.signalAll(); // Notify vendors waiting to add more tickets
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[Customer-{}] Ticket removal interrupted.", customerId, e);
        } finally {
            lock.unlock();
        }
    }
}
