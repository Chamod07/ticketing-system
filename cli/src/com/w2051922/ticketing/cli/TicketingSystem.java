package com.w2051922.ticketing.cli;

import com.w2051922.ticketing.config.SystemConfiguration;

import java.util.Scanner;
import java.util.logging.Logger;

public class TicketingSystem {
    private static final Logger logger = Logger.getLogger(TicketingSystem.class.getName());

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
        SystemConfiguration configuration = new SystemConfiguration(totalTickets, ticketReleaseRate, ticketRetrievalRate, maxTicketCapacity);

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



}