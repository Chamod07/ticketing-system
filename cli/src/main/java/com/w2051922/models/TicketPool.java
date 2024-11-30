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

        // Initialise ticket pool with a set of tickets
        for (int i = 0; i < totalTickets; i++) {
            tickets.add(new Ticket());
        }
    }

    /**
     * Adds a specified number of tickets to the ticket pool for a given vendor.
     * <p>This method will block if adding the specified number of tickets exceeds
     * the maximum capacity, waiting until there is enough space to add the tickets.</p>
     *
     * @param ticketCount the number of tickets to be added to the ticket pool
     * @param vendorId the ID of the vendor adding the tickets
     */
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
     * Removes a specified number of tickets from the ticket pool for a given customer.
     * <p>This method will block if the number of available tickets is less than the requested number,
     * waiting until the requested number of tickets can be removed.</p>
     *
     * @param ticketCount the number of tickets to be removed from the ticket pool
     * @param customerId the ID of the customer requesting the tickets
     */
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
