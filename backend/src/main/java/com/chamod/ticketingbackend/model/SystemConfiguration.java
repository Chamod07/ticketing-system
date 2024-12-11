package com.chamod.ticketingbackend.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Entity class representing the system configuration.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "system_configuration")
public class SystemConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_tickets")
    private int totalTickets;

    @Column(name = "release_rate")
    private int ticketReleaseRate;

    @Column(name = "retrieval_rate")
    private int customerRetrievalRate;

    @Column(name = "max_capacity")
    private int maxTicketCapacity;

    private static final String configFile = "systemConfiguration.json";
    private static final Logger logger = LogManager.getLogger();

    /**
     * Loads the system configuration from a JSON file.
     *
     * @return the loaded SystemConfiguration object, or null if an error occurs
     */
    public static SystemConfiguration loadFromFile() {
        File file = new File(configFile);
        if (!file.exists()) {
            logger.warn("System configuration file does not exist");
            return null;
        }
        try (FileReader reader = new FileReader(file)) {
            synchronized (SystemConfiguration.class) {
                Gson gson = new Gson();
                return gson.fromJson(reader, SystemConfiguration.class);
            }
        } catch (IOException e) {
            logger.error("Error while reading configuration file.");
            return null;
        }
    }

    /**
     * Saves the current system configuration to a JSON file.
     *
     * @throws IOException if an error occurs during writing to the file
     */
    public void saveToFile() throws IOException {
        synchronized (SystemConfiguration.class) {
            try (FileWriter writer = new FileWriter(configFile)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(this, writer);
            }
        }
    }

    @Override
    public String toString() {
        return "[" +
                "totalTickets:" + totalTickets +
                ", ticketReleaseRate:" + ticketReleaseRate +
                ", customerRetrievalRate:" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ']';
    }
}