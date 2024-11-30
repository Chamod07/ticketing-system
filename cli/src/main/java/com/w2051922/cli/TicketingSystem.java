package com.w2051922.cli;

import com.w2051922.config.SystemConfiguration;
import com.w2051922.models.Customer;
import com.w2051922.models.TicketPool;
import com.w2051922.models.Vendor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class TicketingSystem {
    private static final Logger logger = LogManager.getLogger(TicketingSystem.class.getName());
    private static SystemConfiguration configuration;
    private final Scanner scanner = new Scanner(System.in);

    public void systemConfig() {
        System.out.println("***** Event Ticketing System Configuration *****");
        initializeConfig();

        logger.info("System configured with: {}", configuration);

        while (true) {
            System.out.print("Do you want to start the system? (start/stop): ");
            String start = scanner.nextLine().toLowerCase().trim();
            if (start.equals("start")) {
                logger.info("System started.");
                startSystem();
                break;
            } if (start.equals("stop")) {
                logger.info("System stopped. Exiting...");
                break;
            } else {
                System.out.println("Invalid input. Please type 'start' or 'stop'.");
            }
        }
    }

    public void initializeConfig() {
        System.out.print("Load previous system configuration? (yes/no): ");
        while (true) {
            String loadConfig = scanner.nextLine().toLowerCase().trim();
            if (loadConfig.equals("yes")) {
                configuration = new SystemConfiguration();
                configuration = configuration.loadConfiguration();
                if (configuration == null) {
                    setNewConfig();
                }
                break;
            }
            if (loadConfig.equals("no")) {
                setNewConfig();
                break;
            } else System.out.print("Invalid input. Please type 'yes' or 'no': ");
        }
    }

    private void setNewConfig() {
        System.out.println("No previous configuration loaded. Proceeding to new configuration.");

        int totalTickets;
        int maxTicketCapacity;

        while (true) {
            // Total tickets
            System.out.print("Enter total number of tickets: ");
            totalTickets = getValidInput(scanner);

            // Max ticket capacity
            System.out.print("Enter maximum ticket capacity: ");
            maxTicketCapacity = getValidInput(scanner);

            if (maxTicketCapacity > totalTickets) {
                break;
            } else {
                System.out.println("Maximum ticket capacity must be greater than total tickets. Please try again.");
            }
        }

        // Ticket release rate
        System.out.print("Enter ticket release rate (tickets per second): ");
        int ticketReleaseRate = getValidInput(scanner);

        // Ticket retrieval rate
        System.out.print("Enter customer retrieval rate (tickets per seconds): ");
        int ticketRetrievalRate = getValidInput(scanner);

        // create new configuration
        configuration = new SystemConfiguration(totalTickets, ticketReleaseRate, ticketRetrievalRate, maxTicketCapacity);

        // save configuration to a JSON file
        configuration.saveConfiguration();
    }

    private void startSystem() {
        System.out.print("Enter the number of vendors to simulate: ");
        int numberOfVendors = getValidInput(scanner);

        System.out.print("Enter the number of customers to simulate: ");
        int numberOfCustomers = getValidInput(scanner);

        logger.info("Simulating {} vendors and {} customers.", numberOfVendors, numberOfCustomers);

        // Initialize ticket pool
        TicketPool ticketPool = new TicketPool(configuration.getMaxTicketCapacity(), configuration.getTotalTickets());

        // Create vendor threads and store in an array
        List<Thread> vendorThreads = new ArrayList<>();
        for (int i = 1; i <= numberOfVendors; i++) {
            vendorThreads.add(new Thread(new Vendor(ticketPool, String.valueOf(i), configuration.getTicketReleaseRate())));
        }

        // Create customer threads and store in an array
        List<Thread> customerThreads = new ArrayList<>();
        for (int i = 1; i <= numberOfCustomers; i++) {
            customerThreads.add(new Thread(new Customer(ticketPool, String.valueOf(i), configuration.getCustomerRetrievalRate())));
        }

        // Start vendor threads
        for (Thread thread : vendorThreads) {
            thread.start();
        }

        // Start customer threads
        for (Thread thread : customerThreads) {
            thread.start();
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
    }
}