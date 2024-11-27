package com.w2051922.models;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final int maxCapacity;
    private final ConcurrentLinkedQueue<Integer> tickets;
    private final ReentrantLock lock;
    private final Condition notFull;
    private final Condition notEmpty;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new ConcurrentLinkedQueue<>();
        this.lock = new ReentrantLock();
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();
    }

    public void add(int count) throws InterruptedException {
        lock.lock();
        try {
            while (tickets.size() + count > maxCapacity) {
                notFull.await();
            }
            for (int i = 0; i < count; i++) {
                tickets.add(1); // Add a ticket
            }
            System.out.println(count + " tickets added. Current capacity: " + tickets.size());
            notEmpty.signalAll(); // Notify consumers
        } finally {
            lock.unlock();
        }
    }

    public void remove() throws InterruptedException {
        lock.lock();
        try {
            while (tickets.isEmpty()) {
                notEmpty.await();
            }
            tickets.poll(); // Remove a ticket
            System.out.println("Ticket purchased. Current pool size: " + tickets.size());
            notFull.signalAll(); // Notify producers
        } finally {
            lock.unlock();
        }
    }
}
