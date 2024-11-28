package com.w2051922.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class TicketPool {
    private List<Ticket> tickets;
    private int maxCapacity;
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

    /**
     * Add tickets to the ticket pool
     * @param ticketCount number of tickets to sell
     * **/
    public void addTicket(int ticketCount) {
        lock.lock();
        try {
            while (tickets.size() + ticketCount > maxCapacity) {
                logger.warning("Waiting to add tickets. Current size: " + tickets.size() + ", Attempting to add: " + ticketCount + ", Max capacity: " + maxCapacity);
                notFull.await();
            }
            for (int i = 1; i <= ticketCount; i++) {
                tickets.add(new Ticket(i)); // Add a ticket
            }
            System.out.println(ticketCount + " tickets added. Current capacity: " + tickets.size());
            notEmpty.signalAll(); // Notify consumers
        } catch (InterruptedException e) {
            logger.warning("Ticket addition interrupted " + e);
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
                logger.warning("Waiting for tickets to become available");
                notEmpty.await();
            }
            for (int i = 0; i < ticketCount; i++) {
                tickets.removeFirst();
            }
            System.out.println(ticketCount + " ticket(s) purchased. Current pool size: " + tickets.size());
            logger.info("Ticket purchased. Current pool size: " + tickets.size());
            notFull.signalAll(); // Notify producers
        } catch (InterruptedException e) {
            logger.warning("Ticket removal interrupted "+ e);
        } finally {
            lock.unlock();
        }
    }
}
