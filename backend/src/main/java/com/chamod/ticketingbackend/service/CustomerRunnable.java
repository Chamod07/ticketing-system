package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.Customer;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerRunnable implements Runnable{
    @Getter
    private final Customer customer;
    @Getter
    private final int customerId;
    private static int customerCount = 1;
    private final TicketPoolService ticketPool;
    private volatile boolean paused = false;

    private static final Logger logger = LogManager.getLogger(CustomerRunnable.class);

    public CustomerRunnable(Customer customer, TicketPoolService ticketPool) {
        this.customer = customer;
        this.ticketPool = ticketPool;
        this.customerId = customerCount++;
    }

    public static void resetCustomerCount() {
        customerCount = 1;
    }

    public static void removeCustomerCount() {
        customerCount--;
    }

    public void stop() {
        this.paused = true;
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        synchronized (this) {
            paused = false;
            notify();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    while (paused) {
                        wait();
                    }
                }

                if (Thread.currentThread().isInterrupted()) {
                    logger.info("[Customer-{}] Thread interrupted.", customerId);
                    return;
                }
                ticketPool.addTickets(customer.getTicketsPerRetrieval(), customerId);
                Thread.sleep(customer.getRetrievalInterval()*1000L);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[Customer-{}] Interrupted during operation.", customerId);
        }
    }
}
