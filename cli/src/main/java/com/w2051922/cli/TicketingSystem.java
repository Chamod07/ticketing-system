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

    public void systemConfig() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("***** Event Ticketing System Configuration *****");

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

        logger.info("System configured with: {}", configuration);

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
        if (configuration == null) {
            System.out.println("System configuration not available");
            return;
        }

        // Initialize ticket pool
        TicketPool ticketPool = new TicketPool(configuration.getMaxTicketCapacity(), configuration.getTotalTickets());

        // Implement vendor and customer threads
        Vendor vendor1 = new Vendor(ticketPool, "12", 1, configuration.getTicketReleaseRate());
        Vendor vendor2 = new Vendor(ticketPool, "23", 2, configuration.getTicketReleaseRate());

        Customer customer1 = new Customer(ticketPool, "34", 1, configuration.getCustomerRetrievalRate());
        Customer customer2 = new Customer(ticketPool, "56 ", 2, configuration.getCustomerRetrievalRate());

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