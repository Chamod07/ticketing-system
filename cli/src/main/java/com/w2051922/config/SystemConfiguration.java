package com.w2051922.config;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemConfiguration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private static final String configFile = "systemConfiguration.json";
    private static final Logger logger = LogManager.getLogger(SystemConfiguration.class.getName());
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SystemConfiguration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public SystemConfiguration() {}

    public void saveConfiguration() throws IOException {
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(this, writer);
            logger.info("Configuration saved to {}.", configFile);
        }
    }

    public SystemConfiguration loadConfiguration() {
        try (FileReader reader = new FileReader(configFile)) {
            return gson.fromJson(reader, SystemConfiguration.class);
        } catch (FileNotFoundException e) {
            logger.error("Failed to load configuration from {}", configFile, e);
        } catch (IOException e) {

        }
        return null;
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
            logger.warn("Total tickets value is invalid: {}", totalTickets);
            throw new IllegalArgumentException("Total tickets must be greater than 0");
        }
        this.totalTickets = totalTickets;
        logger.info("Total tickets value was set to: {}", totalTickets);
    }
    public void setTicketReleaseRate(int ticketReleaseRate) {
        if (ticketReleaseRate <= 0) {
            logger.warn("Ticket release rate value is invalid: {}", ticketReleaseRate);
            throw new IllegalArgumentException("Ticket release rate must be greater than 0");
        }
        this.ticketReleaseRate = ticketReleaseRate;
        logger.info("Ticket release rate value was set to: {}", ticketReleaseRate);
    }
    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        if (customerRetrievalRate <= 0) {
            logger.warn("Customer retrieval rate value is invalid: {}", customerRetrievalRate);
            throw new IllegalArgumentException("Customer retrieval rate must be greater than 0");
        }
        this.customerRetrievalRate = customerRetrievalRate;
        logger.info("Customer retrieval rate value was set to: {}", customerRetrievalRate);
    }
    public void setMaxTicketCapacity(int maxTicketCapacity) {
        if (maxTicketCapacity <= 0) {
            logger.warn("Max ticket capacity value is invalid: {}", maxTicketCapacity);
            throw new IllegalArgumentException("Max ticket capacity must be greater than 0");
        }
        this.maxTicketCapacity = maxTicketCapacity;
        logger.info("Max ticket capacity value was set to: {}", maxTicketCapacity);
    }

    @Override
    public String toString() {
        return "[" +
                "totalTickets = " + totalTickets +
                ", ticketReleaseRate = " + ticketReleaseRate +
                ", customerRetrievalRate = " + customerRetrievalRate +
                ", maxTicketCapacity = " + maxTicketCapacity +
                "]";
    }
}