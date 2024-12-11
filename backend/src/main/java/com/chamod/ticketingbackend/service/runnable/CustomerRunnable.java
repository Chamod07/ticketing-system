package com.chamod.ticketingbackend.service.runnable;

import com.chamod.ticketingbackend.model.Customer;
import com.chamod.ticketingbackend.service.TicketPoolService;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Runnable implementation for handling customer ticket retrieval.
 */
public class CustomerRunnable implements Runnable {
    @Getter
    private final Customer customer;
    @Getter
    private final int customerId;
    private static int customerCount = 1;
    private static int vipCustomerCount = 1;
    private final boolean isVip;
    private final TicketPoolService ticketPool;
    private volatile boolean paused = false;

    private static final Logger logger = LogManager.getLogger(CustomerRunnable.class);

    /**
     * Constructor for CustomerRunnable.
     *
     * @param customer the customer object
     * @param ticketPool the ticket pool service
     * @param isVip whether the customer is a VIP
     */
    public CustomerRunnable(Customer customer, TicketPoolService ticketPool, boolean isVip) {
        this.customer = customer;
        this.ticketPool = ticketPool;
        this.isVip = isVip;
        this.customerId = isVip ? vipCustomerCount++ : customerCount++;
    }

    /**
     * Resets the customer count to 1.
     */
    public static void resetCustomerCount() {
        customerCount = 1;
    }

    /**
     * Resets the VIP customer count to 1.
     */
    public static void resetVipCustomerCount() {
        vipCustomerCount = 1;
    }

    /**
     * Decreases the customer count by 1.
     */
    public static void removeCustomerCount() {
        customerCount--;
    }

    /**
     * Decreases the VIP customer count by 1.
     */
    public static void removeVipCustomerCount() {
        vipCustomerCount--;
    }

    /**
     * Stops the runnable.
     */
    public void stop() {
        this.paused = true;
    }

    /**
     * Pauses the runnable.
     */
    public void pause() {
        this.paused = true;
    }

    /**
     * Resumes the runnable.
     */
    public void resume() {
        synchronized (this) {
            paused = false;
            notify();
        }
    }

    /**
     * The main logic of the runnable, which retrieves tickets at intervals.
     */
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
                    logger.info("[{}Customer-{}] Thread interrupted.", isVip ? "VIP " : "", customerId);
                    return;
                }
                ticketPool.removeTickets(customer.getTicketsPerRetrieval(), customerId, isVip);
                Thread.sleep(customer.getRetrievalInterval() * 1000L);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[{}Customer-{}] Interrupted during operation.", isVip ? "VIP " : "", customerId);
        }
    }
}