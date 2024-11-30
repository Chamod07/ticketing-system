package com.w2051922.cli;

import com.w2051922.config.SystemConfiguration;
import com.w2051922.models.Customer;
import com.w2051922.models.TicketPool;
import com.w2051922.models.Vendor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class TicketingSystem {
    private static final Logger logger = LogManager.getLogger(TicketingSystem.class.getName());
    private static SystemConfiguration configuration;
    private final Scanner scanner = new Scanner(System.in);

    public void systemConfig() {

        System.out.println("***** Event Ticketing System Configuration *****");
        initializeConfig();

        logger.info("System configured with: {}", configuration);

        System.out.println("Type 'start' to begin: ");
    }

    public void initializeConfig() {
        System.out.print("Load previous system configuration? (yes/no): ");
        String loadConfig = scanner.nextLine().toLowerCase().trim();
        if (loadConfig.equals("yes")) {
            configuration = new SystemConfiguration();
            configuration = configuration.loadConfiguration();
            System.out.println("Loaded previous system configuration.");
        } else {
            System.out.println("No previous configuration loaded. Proceeding to new configuration.");
            // Total tickets
            System.out.print("Enter total number of tickets: ");
            int totalTickets = getValidInput(scanner);

            // Ticket release rate
            System.out.print("Enter ticket release rate (in seconds): ");
            int ticketReleaseRate = getValidInput(scanner);

            // Ticket retrieval rate
            System.out.print("Enter customer retrieval rate (in seconds): ");
            int ticketRetrievalRate = getValidInput(scanner);

            // Max ticket capacity
            System.out.print("Enter maximum ticket capacity: ");
            int maxTicketCapacity = getValidInput(scanner);

            // create configuration
            configuration = new SystemConfiguration(totalTickets, ticketReleaseRate, ticketRetrievalRate, maxTicketCapacity);

            try {
                configuration.saveConfiguration();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getValidInput(Scanner scanner) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input > 0) {
                    return input;
                }
                System.out.print("Please enter a number greater than 0: ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    public static void main(String[] args) {
        TicketingSystem ticketingSystem = new TicketingSystem();
        ticketingSystem.systemConfig();

        // Initialize ticket pool
        TicketPool ticketPool = new TicketPool(configuration.getMaxTicketCapacity(), configuration.getTotalTickets());

        // Implement vendor and customer threads
        Vendor vendor1 = new Vendor(ticketPool, "1", 1, configuration.getTicketReleaseRate());
        Vendor vendor2 = new Vendor(ticketPool, "2", 1, configuration.getTicketReleaseRate());

        Customer customer1 = new Customer(ticketPool, "1", 1, configuration.getCustomerRetrievalRate());
        Customer customer2 = new Customer(ticketPool, "2", 1, configuration.getCustomerRetrievalRate());

        Thread vendor1Thread = new Thread(vendor1);
        Thread vendor2Thread = new Thread(vendor2);

        Thread customer1Thread = new Thread(customer1);
        Thread customer2Thread = new Thread(customer2);

        vendor1Thread.start();
        vendor2Thread.start();

        customer1Thread.start();
        customer2Thread.start();

    }

}