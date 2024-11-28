package com.w2051922.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private List<Ticket> tickets;
    private int maxCapacity;
    private int totalTickets;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition notFull;
    private final Condition notEmpty;
    private static final Logger logger = LogManager.getLogger(TicketPool.class);

    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
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
    public void addTicket(int ticketCount) {
        lock.lock();
        try {
            // Make sure not to go beyond max capacity
            int availableSpace = maxCapacity - tickets.size();
            int ticketsToAdd = Math.min(ticketCount, availableSpace);

            if (ticketsToAdd > 0) {
                for (int i = 0; i < ticketsToAdd; i++) {
                    tickets.add(new Ticket());
                }
                totalTickets = tickets.size(); // Assign the current size of ticket pool to totalTickets
                logger.info("Added {} tickets. Current pool size: {}", ticketsToAdd, totalTickets);
                notEmpty.signalAll(); // Notify customers
            } else {
                logger.warn("Cannot add tickets. Pool is at max capacity.");
                notFull.await(); // Hold threads until tickets are available
            }
        } catch (InterruptedException e) {
            logger.error("Ticket addition interrupted.");
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Remove tickets from ticket pool
     * @param ticketCount number of tickets to buy
     * **/
    public void removeTicket(int ticketCount) {
        lock.lock();
        try {
            while (tickets.isEmpty()) {
                logger.warn("No tickets available. Waiting for tickets to be added.");
                notEmpty.await();
            }
            // To make sure not to remove more tickets than available
            int ticketsToRemove = Math.min(ticketCount, tickets.size());
            for (int i = 0; i < ticketsToRemove; i++) {
                tickets.remove(0);
            }

            totalTickets = tickets.size();
            logger.info("Purchased {} tickets. Remaining pool size: {}", ticketsToRemove, totalTickets);
            notFull.signalAll(); // Notify vendors
        } catch (InterruptedException e) {
            logger.error("Ticket removal interrupted.");
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
