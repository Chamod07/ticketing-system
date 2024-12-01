package com.w2051922.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private String customerId;
    private int retrievalRate;
    private volatile boolean running = true;
    private static final Logger logger = LogManager.getLogger(Customer.class.getName());

    public Customer(TicketPool ticketPool, String customerId, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.customerId = customerId;
        this.retrievalRate = retrievalRate;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public int getRetrievalRate() {
        return retrievalRate;
    }
    public void setRetrievalRate(int retrievalRate) {
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        try {
            while (running) {
                Thread.sleep(1000); // Wait for 1 second
                ticketPool.removeTicket(retrievalRate, customerId); // Purchase ticket
            }
        } catch (InterruptedException e) {
            logger.error("Customer interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            stop();
        }
    }

    public void stop() {
        running = false;
    }

    @Override
    public String toString() {
        return customerId + "\t" + retrievalRate;
    }
}
