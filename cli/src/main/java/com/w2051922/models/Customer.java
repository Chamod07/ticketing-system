package com.w2051922.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Customer class represents a customer that retrieves tickets from a TicketPool.
 * It implements the Runnable interface to allow running in a separate thread.
 */
public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private String customerId;
    private int retrievalRate;
    private volatile boolean running = true;
    private static final Logger logger = LogManager.getLogger(Customer.class.getName());

    /**
     * Constructs a new Customer.
     *
     * @param ticketPool the TicketPool from which the customer retrieves tickets
     * @param customerId the ID of the customer
     * @param retrievalRate the rate at which the customer retrieves tickets
     */
    public Customer(TicketPool ticketPool, String customerId, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.customerId = customerId;
        this.retrievalRate = retrievalRate;
    }

    /**
     * Gets the customer ID.
     *
     * @return the customer ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID.
     *
     * @param customerId the new customer ID
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the retrieval rate.
     *
     * @return the retrieval rate
     */
    public int getRetrievalRate() {
        return retrievalRate;
    }

    /**
     * Sets the retrieval rate.
     *
     * @param retrievalRate the new retrieval rate
     */
    public void setRetrievalRate(int retrievalRate) {
        this.retrievalRate = retrievalRate;
    }

    /**
     * Runs the customer thread, which retrieves tickets from the TicketPool at the specified rate.
     */
    @Override
    public void run() {
        try {
            while (running) {
                Thread.sleep(1000); // Wait for 1 second
                ticketPool.removeTicket(retrievalRate, customerId); // Purchase ticket
            }
        } catch (InterruptedException e) {
            logger.error("[Customer-{}] Interrupted.", customerId);
            Thread.currentThread().interrupt();
        } finally {
            logger.info("[Customer-{}] Customer has been terminated.", customerId);
        }
    }

    /**
     * Stops the customer thread.
     */
    public void stop() {
        running = false;
    }

    /**
     * Returns a string representation of the customer.
     *
     * @return a string representation of the customer
     */
    @Override
    public String toString() {
        return customerId + "\t" + retrievalRate;
    }
}
