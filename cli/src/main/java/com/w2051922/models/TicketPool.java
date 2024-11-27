package com.w2051922.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketPool {
    private final List<Ticket> tickets;
    private final int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull;
    private final Condition notEmpty;
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();
    }

    public void add(int count) throws InterruptedException {
        if (count <= 0) {
            throw new IllegalArgumentException("Cannot add negative number of tickets");
        }
        lock.lock();
        try {
            while (tickets.size() + count > maxCapacity) {
                logger.warning("Waiting to add tickets. Current size: " + tickets.size() + ", Attempting to add: " + count + ", Max capacity: " + maxCapacity);
                notFull.await();
            }
            for (int i = 0; i < count; i++) {
                tickets.add(new Ticket()); // Add a ticket
            }
            System.out.println(count + " tickets added. Current capacity: " + tickets.size());
            notEmpty.signalAll(); // Notify consumers
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Ticket addition interrupted", e);
            Thread.currentThread().interrupt(); // Restore interrupt status
            throw e;
        } finally {
            lock.unlock();
        }
    }

    public void remove() throws InterruptedException {
        lock.lock();
        try {
            while (tickets.isEmpty()) {
                logger.warning("Waiting for tickets to become available");
                notEmpty.await();
            }
            tickets.removeFirst(); // Remove a ticket
            System.out.println("Ticket purchased. Current pool size: " + tickets.size());
            logger.info("Ticket purchased. Current pool size: " + tickets.size());
            notFull.signalAll(); // Notify producers
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Ticket removal interrupted", e);
            Thread.currentThread().interrupt(); // Restore interrupt status
            throw e;
        } finally {
            lock.unlock();
        }
    }
}
