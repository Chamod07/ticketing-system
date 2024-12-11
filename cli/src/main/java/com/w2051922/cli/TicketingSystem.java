package com.w2051922.cli;

import com.w2051922.config.SystemConfiguration;
import com.w2051922.models.Customer;
import com.w2051922.models.TicketPool;
import com.w2051922.models.Vendor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The TicketingSystem class manages the configuration and simulation of a ticketing system.
 * It handles the initialization of system settings, starting and stopping of vendor and customer threads,
 * and user interactions for configuring and running the system.
 */
public class TicketingSystem {
    private static final Logger logger = LogManager.getLogger(TicketingSystem.class.getName());
    private static SystemConfiguration configuration;
    private final Scanner scanner = new Scanner(System.in);
    private List<Thread> customerThreads;
    private List<Thread> vendorThreads;

    /**
     * Configures and initiates the startup process for the ticketing system.
     * <p>
     * This method performs the following steps:
     * <ol>
     * <li>Initializes the configuration settings.</li>
     * <li>Logs the loaded configuration details.</li>
     * <li>Prompts the user to <code>start</code> or <code>reset</code> the system</li>
     * </ol>
     */
    public void systemConfig() {
        initializeConfig();
        logger.info("System configured with: {}", configuration);
        System.out.print("Enter the number of vendors to simulate: ");
        int numberOfVendors = getValidInput(scanner);
        System.out.print("Enter the number of customers to simulate: ");
        int numberOfCustomers = getValidInput(scanner);
        System.out.print("Do you want to start or reset the system? (start/reset): ");
        while (true) {
            String start = scanner.nextLine().toLowerCase().trim();
            if (start.equals("start")) {
                startSystem(numberOfVendors, numberOfCustomers);
                break;
            } if (start.equals("reset")) {
                logger.info("Request for system configuration reset.");
                systemConfig();
                break;
            } else {
                System.out.print("Invalid input. Please type 'start' or 'reset': ");
            }
        }
    }

    /**
     * Initializes the system configuration by prompting the user to load
     * a previous configuration or create a new one.
     * <p>
     * The method begins by asking the user if they wish to load a previous
     * system configuration. If the user enters "yes", the method attempts
     * to load an existing configuration using the {@code loadConfiguration} method
     * of the {@link SystemConfiguration} class. If loading the configuration fails,
     * a new configuration is created by invoking the {@code setNewConfig} method.
     * If the user enters "no", a new configuration is directly set up.
     * </p>
     * <p>
     * The configuration process continues in a loop until the user provides
     * a valid response of either "yes" or "no".
     * </p>
     */
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

    /**
     * Creates a new system configuration for ticketing by gathering user input
     * for total tickets, maximum ticket capacity, ticket release rate, and
     * customer retrieval rate.
     * <p>
     * This method enters an interactive loop to ensure the maximum ticket capacity
     * is greater than the total number of tickets. The user is prompted to enter
     * valid values until the condition is satisfied. Once valid values are obtained,
     * a new {@link SystemConfiguration} object is created and saved to a JSON file.
     * </p>
     * <p>
     * Intended for internal use within the initialization process of a ticketing
     * system when no configuration is previously loaded.
     * </p>
     */
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

    /**
     * Starts the simulation of a ticketing system by initializing the specified number of {@linkplain Vendor} and {@linkplain Customer}.
     * <ol>
     * <li>Prompts the user to input the number of vendors and customers to simulate.</li>
     * <li>Logs the start of the simulation with the provided number of vendors and customers.</li>
     * <li>Initializes a {@link TicketPool} with a maximum capacity and total number of tickets from the configuration.</li>
     * <li>Creates and starts {@code threads} for the specified number of vendors, where each vendor has its own ticket release rate.</li>
     * <li>Creates and starts {@code threads} for the specified number of customers, where each customer has its own ticket retrieval rate.</li>
     * </ol>
     * <p>The method uses the {@link TicketPool} instance to manage the tickets distributed and retrieved by the vendor and customer threads.</p>
     * @param numOfVendors the number of vendors to simulate
     * @param numOfCustomers the number of customers to simulate
     */
    private void startSystem(int numOfVendors, int numOfCustomers) {
        logger.info("System started with {} vendors and {} customers.", numOfVendors, numOfCustomers);
        // Initialize ticket pool
        TicketPool ticketPool = new TicketPool(configuration.getMaxTicketCapacity(), configuration.getTotalTickets());
        // Display "Press 'Enter' to stop" message with a delay
        System.out.print("\nPress 'Enter' to stop the system");
        try {
            for (int i = 0; i < 3; i++) {
                System.out.print(".");
                Thread.sleep(1000);
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
        // Create vendor threads and store in an array
        vendorThreads = new ArrayList<>();
        for (int i = 1; i <= numOfVendors; i++) {
            vendorThreads.add(new Thread(new Vendor(ticketPool, String.valueOf(i), configuration.getTicketReleaseRate())));
            logger.info("[Vendor-{}] Thread initialised.", i);
        }
        // Create customer threads and store in an array
        customerThreads = new ArrayList<>();
        for (int i = 1; i <= numOfCustomers; i++) {
            customerThreads.add(new Thread(new Customer(ticketPool, String.valueOf(i), configuration.getCustomerRetrievalRate())));
            logger.info("[Customer-{}] Thread initialised.", i);
        }
        // Start vendor threads
        for (int i = 0; i < vendorThreads.size(); i++) {
            Thread thread = vendorThreads.get(i);
            thread.start();
            logger.info("[Vendor-{}] Thread started.", (i+1));
        }
        // Start customer threads
        for (int i = 0; i < customerThreads.size(); i++) {
            Thread thread = customerThreads.get(i);
            thread.start();
            logger.info("[Customer-{}] Thread started.", (i+1));
        }
        // Wait for user input to stop
        scanner.nextLine(); // Wait for Enter key
        // Stop all threads
        stopAllThreads();
        // Ask if user wants to simulate another event
        simulateAnotherEvent();
    }

    /**
     * Stops all vendor and customer threads.
     */
    private void stopAllThreads() {
        logger.info("Stopping all threads.");
        for (Thread thread : vendorThreads) {
            thread.interrupt();
        }
        for (Thread thread : customerThreads) {
            thread.interrupt();
        }
        for (Thread thread : vendorThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        for (Thread thread : customerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        logger.info("All threads stopped.");
    }

    /**
     * Asks user if they want to simulate another event.
     */
    private void simulateAnotherEvent() {
        System.out.println();
        System.out.print("Do you want to simulate another event? (yes/no): ");
        while (true) {
            String response = scanner.nextLine().toLowerCase().trim();
            if (response.equals("yes")) {
                logger.info("Simulating another event.");
                systemConfig();
                break;
            } else if (response.equals("no")) {
                logger.info("System stopped. Exiting...");
                break;
            } else {
                System.out.print("Invalid input. Please type 'yes' or 'no': ");
            }
        }
    }

    /**
     * Reads and returns a valid positive integer input from the user.
     * <p>This method repeatedly prompts the user until a valid number greater than zero is entered.</p>
     *
     * @param scanner the {@link Scanner} object used to read user input
     * @return a valid integer input from the user that is greater than zero
     */
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

    /**
     * The main method to start the ticketing system.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        TicketingSystem ticketingSystem = new TicketingSystem();
        System.out.println();
        System.out.println("====== \t Event Ticketing System Configuration \t ======");
        System.out.println();
        ticketingSystem.systemConfig();
    }
}