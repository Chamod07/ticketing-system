package com.w2051922.cli;

import com.w2051922.config.SystemConfiguration;
import com.w2051922.models.Customer;
import com.w2051922.models.TicketPool;
import com.w2051922.models.Vendor;

import java.util.Scanner;
import java.util.logging.Logger;

public class TicketingSystem {
    private static final Logger logger = Logger.getLogger(TicketingSystem.class.getName());
    private static SystemConfiguration configuration;

    public void systemConfig() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("***** Event Ticketing System Configuration *****");

        // Total tickets
        System.out.print("Enter total number of tickets: ");
        int totalTickets = getValidInput(scanner);

        // Ticket release rate
        System.out.print("Enter ticket release rate (tickets per minute): ");
        int ticketReleaseRate = getValidInput(scanner);

        // Ticket retrieval rate
        System.out.print("Enter customer retrieval rate (tickets per minute): ");
        int ticketRetrievalRate = getValidInput(scanner);

        // Max ticket capacity
        System.out.print("Enter maximum ticket capacity: ");
        int maxTicketCapacity = getValidInput(scanner);

        // create configuration
        configuration = new SystemConfiguration(totalTickets, ticketReleaseRate, ticketRetrievalRate, maxTicketCapacity);

        logger.info("System configured with: "+ configuration);
    }

    private int getValidInput(Scanner scanner) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input > 0) {
                    return input;
                }
                System.out.print("Please enter a number greater than 0: ");
            } catch (NumberFormatException ex) {
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
        TicketPool ticketPool = new TicketPool(configuration.getMaxTicketCapacity());

        // Implement vendor and customer threads
        Vendor vendor1 = new Vendor(ticketPool, "12", 5, configuration.getTicketReleaseRate());
        Vendor vendor2 = new Vendor(ticketPool, "23", 10, configuration.getTicketReleaseRate());

        Customer customer1 = new Customer(ticketPool, "34", configuration.getCustomerRetrievalRate());
        Customer customer2 = new Customer(ticketPool, "65", configuration.getCustomerRetrievalRate());

        Thread vendor1Thread = new Thread(vendor1);
        Thread vendor2Thread = new Thread(vendor2);

        Thread customer1Thread = new Thread(customer1);
        Thread customer2Thread = new Thread(customer2);

        vendor1Thread.start();
        vendor2Thread.start();

        customer1Thread.start();
        customer2Thread.start();

        try {
            vendor1Thread.join();
            vendor2Thread.join();
            customer1Thread.join();
            customer2Thread.join();

        } catch (InterruptedException e) {
            System.out.println("Interrupted: "+e.getMessage());
        }

        System.out.println("Ticket pool is empty. Shutting down...");
    }

}