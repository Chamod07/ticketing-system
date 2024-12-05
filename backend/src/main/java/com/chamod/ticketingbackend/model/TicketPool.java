package com.chamod.ticketingbackend.model;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class TicketPool {
    private final LinkedList<Ticket> tickets = new LinkedList<>();
    private int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final Logger logger = org.apache.logging.log4j.LogManager.getLogger(TicketPool.class);

    public void addTickets(int ticketCount, Long vendorId) {
        lock.lock();
        try {
            while (tickets.size() + ticketCount > maxCapacity) {
                logger.warn("[Vendor-{}] Pool is full. Waiting to add tickets (Pool size-{}).", vendorId, tickets.size());
                notFull.await();
            }

            for (int i = 0; i < ticketCount; i++) {
                tickets.add(new Ticket());
            }

            logger.info("[Vendor-{}] Added {} tickets. (Pool size-{})", vendorId, ticketCount, tickets.size());
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[Vendor-{}] Ticket addition interrupted.", vendorId, e);
        } finally {
            lock.unlock();
        }
    }
}
