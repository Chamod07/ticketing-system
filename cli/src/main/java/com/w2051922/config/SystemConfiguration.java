package com.w2051922.config;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * SystemConfiguration class represents the configuration settings for the system.
 * It includes parameters such as total tickets, ticket release rate, customer retrieval rate,
 * and maximum ticket capacity. The class provides methods to save and load the configuration
 * to and from a JSON file.
 */
public class SystemConfiguration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private static final String configFile = "systemConfiguration.json";
    private static final Logger logger = LogManager.getLogger(SystemConfiguration.class.getName());
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Constructs a SystemConfiguration object with specified parameters.
     *
     * @param totalTickets the total number of tickets
     * @param ticketReleaseRate the rate at which tickets are released
     * @param customerRetrievalRate the rate at which customers retrieve tickets
     * @param maxTicketCapacity the maximum capacity of tickets
     */
    public SystemConfiguration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    /**
     * Default constructor for SystemConfiguration.
     */
    public SystemConfiguration() {}

    /**
     * Saves the current system configuration to a JSON file.
     * This method uses the Gson library to serialize the current state
     * of the SystemConfiguration object and writes it to a file.
     * A successful save operation will log an informational message indicating
     * the name of the file to which the configuration has been saved.
     * If an IOException occurs during the file writing process, a warning
     * message will be logged with details about the failure.
     */
    public void saveConfiguration() {
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(this, writer);
            logger.info("Configuration saved to {}.", configFile);
        } catch (IOException e) {
            logger.warn("Failed to save configuration to {}", configFile);
        }
    }

    /**
     * Loads the system configuration from a JSON file.
     * This method attempts to read a configuration file specified by the configFile field
     * and deserialize it into a SystemConfiguration object using the Gson library.
     * If the file is not found, an error message is logged. In case of an IOException
     * during the reading process, a warning message is logged.
     *
     * @return the deserialized SystemConfiguration object if successful;
     *         otherwise, returns null if the configuration file was not found or an error occurred.
     */
    public SystemConfiguration loadConfiguration() {
        try (FileReader reader = new FileReader(configFile)) {
            logger.info("Loading configuration from {}.", configFile);
            return gson.fromJson(reader, SystemConfiguration.class);
        } catch (FileNotFoundException e) {
            logger.error("Configuration file named {} not found.", configFile);
        } catch (IOException e) {
            logger.warn("Failed to load configuration from {}", configFile);
        }
        return null;
    }

    // Getters and Setters

    /**
     * Gets the total number of tickets.
     *
     * @return the total number of tickets
     */
    public int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Gets the ticket release rate.
     *
     * @return the ticket release rate
     */
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    /**
     * Gets the customer retrieval rate.
     *
     * @return the customer retrieval rate
     */
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    /**
     * Gets the maximum ticket capacity.
     *
     * @return the maximum ticket capacity
     */
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    /**
     * Sets the total number of tickets.
     *
     * @param totalTickets the total number of tickets
     */
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    /**
     * Sets the ticket release rate.
     *
     * @param ticketReleaseRate the ticket release rate
     */
    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    /**
     * Sets the maximum ticket capacity.
     *
     * @param maxTicketCapacity the maximum ticket capacity
     */
    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    /**
     * Sets the customer retrieval rate.
     *
     * @param customerRetrievalRate the customer retrieval rate
     */
    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    /**
     * Returns a string representation of the SystemConfiguration object.
     *
     * @return a string representation of the SystemConfiguration object
     */
    @Override
    public String toString() {
        return "[" +
                "Total Tickets = " + totalTickets +
                ", Ticket Release Rate = " + ticketReleaseRate +
                ", Customer Retrieval Rate = " + customerRetrievalRate +
                ", Maximum Ticket Capacity = " + maxTicketCapacity +
                "]";
    }
}