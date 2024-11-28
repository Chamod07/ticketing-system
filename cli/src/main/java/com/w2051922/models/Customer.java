package com.w2051922.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private String customerId;
    private int ticketsPerRetrieval;
    private int retrievalInterval;
    private volatile boolean running = true;
    private static final Logger logger = LogManager.getLogger(Customer.class.getName());

    public Customer(TicketPool ticketPool, String customerId, int ticketsPerRetrieval, int retrievalInterval) {
        this.ticketPool = ticketPool;
        this.customerId = customerId;
        this.ticketsPerRetrieval = ticketsPerRetrieval;
        this.retrievalInterval = retrievalInterval;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public int getRetrievalInterval() {
        return retrievalInterval;
    }
    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }
    public int getTicketsPerRetrieval() {
        return ticketsPerRetrieval;
    }
    public void setTicketsPerRetrieval(int ticketsPerRetrieval) {
        this.ticketsPerRetrieval = ticketsPerRetrieval;
    }

    @Override
    public void run() {
        try {
            while (running) {
                ticketPool.removeTicket(ticketsPerRetrieval); // Purchase ticket
                Thread.sleep(retrievalInterval * 1000L); // Wait for the retrieval rate
            }
        } catch (InterruptedException e) {
            logger.error("Customer interrupted", e);
        }
    }

    public void stop() {
        running = false;
    }

    @Override
    public String toString() {
        return customerId + "\t" + ticketsPerRetrieval + "\t" + retrievalInterval;
    }
}
