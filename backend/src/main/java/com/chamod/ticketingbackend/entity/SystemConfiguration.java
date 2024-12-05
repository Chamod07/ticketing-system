package com.chamod.ticketingbackend.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "system_configuration")
public class SystemConfiguration {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_tickets")
    private int totalTicket;

    @Column(name = "release_rate")
    private int releaseRate;

    @Column(name = "release_interval")
    private int retrievalRate;

    @Column(name = "max_capacity")
    private int maxCapacity;

    private static final String configFile = "systemConfiguration.json";

    // Load configuration from JSON file
    public static SystemConfiguration loadFromFile() throws IOException{
        File file = new File(configFile);
        if (!file.exists()) {
            throw new IOException("Configuration file doesn't exist!");
        }
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, SystemConfiguration.class);
        }
    }

    // Save configuration to JSON file
    public void saveToFile() throws IOException{
        try (FileWriter writer = new FileWriter(configFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, writer);
        }
    }

}
