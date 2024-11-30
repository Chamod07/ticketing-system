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

    /**
     * Configures and initiates the startup process for the ticketing system.
     * <p>
     * This method performs the following steps:
     * <ol>
     * <li>Initializes of the configuration settings.</li>
     * <li>Logs the loaded configuration details.</li>
     * <li>Prompt the user to <code>start</code> or <code>stop</code> the system</li>
     * </ol>
     */
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

    /**
     * <p>
     * Initializes the system configuration by prompting the user to load
     * a previous configuration or create a new one.
     * </p>
     *<p>
     * The method begins by asking the user if they wish to load a previous
     * system configuration. If the user enters "yes", the method attempts
     * to load an existing configuration using the {@code  loadConfiguration} method
     * of the {@link SystemConfiguration} class. If loading the configuration fails,
     * a new configuration is created by invoking the {@code setNewConfig} method.
     * If the user enters "no", a new configuration is directly set up.
     *</p>
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
     * a new {@link SystemConfiguration} object is created and subsequently saved to a JSON file.
     * </p>
     * <p>
     * Intended for internal use within the initialization process of a ticketing
     * system when no configuration is previously loaded.
     *</p>
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
     * <p>Starts the simulation of a ticketing system by initializing the specified number of {@linkplain Vendor} and {@linkplain Customer}.</p>
     * <ol>
     * <li>Prompts the user to input the number of vendors and customers to simulate.</li>
     * <li>Logs the start of the simulation with the provided number of vendors and customers.</li>
     * <li>Initializes a {@link TicketPool} with a maximum capacity and total number of tickets from the configuration.</li>
     * <li>Creates and starts {@code threads} for the specified number of vendors, where each vendor has its own ticket release rate.</li>
     * <li>Creates and starts {@code threads} for the specified number of customers, where each customer has its own ticket retrieval rate.</li>
     * </ol>
     * <p>The method uses the {@link TicketPool} instance to manage the tickets distributed and retrieved by the vendor and customer threads.</p>
     */
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

    public static void main(String[] args) {
        TicketingSystem ticketingSystem = new TicketingSystem();
        ticketingSystem.systemConfig();
    }
}