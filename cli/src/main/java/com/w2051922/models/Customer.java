package com.w2051922.models;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private String customerId;
    private int retrievalInterval;

    public Customer(TicketPool ticketPool, String customerId, int retrievalInterval) {
        this.ticketPool = ticketPool;
        this.customerId = customerId;
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

    @Override
    public void run() {
        try {
            while (true) {
                ticketPool.remove(); // Purchase 1 ticket
                Thread.sleep(retrievalInterval * 1000L); // Wait for the retrieval rate
            }
        } catch (InterruptedException e) {
            System.out.println("Customer interrupted.");
        }
    }
}
