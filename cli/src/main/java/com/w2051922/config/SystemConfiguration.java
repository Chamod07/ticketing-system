package com.w2051922.config;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SystemConfiguration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    private static final Logger logger = Logger.getLogger(SystemConfiguration.class.getName());
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SystemConfiguration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void saveConfiguration() throws IOException {
        try (FileWriter writer = new FileWriter("systemConfiguration.json")) {
            gson.toJson(this, writer);
            System.out.println("Configuration saved to 'systemConfiguration.json'.");
        }
    }

    public static SystemConfiguration loadConfiguration(String configFile) throws IOException {
        try (FileReader reader = new FileReader(configFile)) {
            return gson.fromJson(reader, SystemConfiguration.class);
        }
    }

    // Getters and Setters
    public int getTotalTickets() {
        return totalTickets;
    }
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setTotalTickets(int totalTickets) {
        if (totalTickets <= 0) {
            logger.warning("Total tickets value is invalid: "+totalTickets);
            throw new IllegalArgumentException("Total tickets must be greater than 0");
        }
        this.totalTickets = totalTickets;
        logger.info("Total tickets value was set to: "+totalTickets);
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        if (ticketReleaseRate <= 0) {
            logger.warning("Ticket release rate value is invalid: "+ticketReleaseRate);
            throw new IllegalArgumentException("Ticket release rate must be greater than 0");
        }
        this.ticketReleaseRate = ticketReleaseRate;
        logger.info("Ticket release rate value was set to: "+ticketReleaseRate);
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        if (customerRetrievalRate <= 0) {
            logger.warning("Customer retrieval rate value is invalid: "+customerRetrievalRate);
            throw new IllegalArgumentException("Customer retrieval rate must be greater than 0");
        }
        this.customerRetrievalRate = customerRetrievalRate;
        logger.info("Customer retrieval rate value was set to: "+customerRetrievalRate);
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        if (maxTicketCapacity <= 0) {
            logger.warning("Max ticket capacity value is invalid: "+maxTicketCapacity);
            throw new IllegalArgumentException("Max ticket capacity must be greater than 0");
        }
        this.maxTicketCapacity = maxTicketCapacity;
        logger.info("Max ticket capacity value was set to: "+maxTicketCapacity);
    }

    @Override
    public String toString() {
        return "SystemConfiguration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }
}