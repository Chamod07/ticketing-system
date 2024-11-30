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

    /**
     * <p>Save the current system configuration to a JSON file.</p>
     *
     * <p>This method uses the {@link Gson} library to serialize the current state
     * of the {@linkplain SystemConfiguration} object and writes it to a file.</p>
     *
     * <p>A successful save operation will log an informational message indicating
     * the name of the file to which the configuration has been saved.</p>
     *
     * <p>If an {@link IOException} occurs during the file writing process, a warning
     * message will be logged with details about the failure.</p>
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
     * <p>Load the system configuration from a JSON file.</p>
     *
     * <p>This method attempts to read a configuration file specified by the {@code configFile} field
     * and deserialize it into a {@linkplain SystemConfiguration} object using the {@link Gson} library.
     * If the file is not found, an error message is logged. In case of an {@link IOException}
     * during the reading process, a warning message is logged.</p>
     *
     * @return the deserialized {@linkplain SystemConfiguration} object if successful;
     *         otherwise, returns {@code null} if the configuration file was not found or an error occurred.
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
                "Total Tickets = " + totalTickets +
                ", Ticket Release Rate = " + ticketReleaseRate +
                ", Customer Retrieval Rate = " + customerRetrievalRate +
                ", Maximum Ticket Capacity = " + maxTicketCapacity +
                "]";
    }
}