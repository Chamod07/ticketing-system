package com.w2051922.ticketing.cli;

import java.util.Scanner;

public class TicketingSystem {
    private static final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Welcome to the Ticketing System!");
        while (true) {
            displayMenu();
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "1":
                    configureSystem();
                    break;
                case "2":
                    startSystem();
                    break;
                case "3":
                    stopSystem();
                    break;
                case "4":
                    showStatus();
                    break;
                case "exit":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n===== Ticket System Menu =====");
        System.out.println("1. Configure System");
        System.out.println("2. Start System");
        System.out.println("3. Stop System");
        System.out.println("4. Show Status");
        System.out.println("Type 'exit' to quit");
        System.out.print("Enter your choice: ");
    }

    private void configureSystem() {
        System.out.println("\n===== System Configuration =====");

        try {
            System.out.print("Enter number of tickets to be released: ");
            int totalTickets = Integer.parseInt(scanner.nextLine());
            if (totalTickets <= 0) throw new IllegalArgumentException("Total tickets must be greater than 0");
            setTotalTickets(totalTickets);

            System.out.print("Enter ticket release rate in seconds: ");
            int ticketReleaseRate = Integer.parseInt(scanner.nextLine());
            if (ticketReleaseRate <= 0)
                throw new IllegalArgumentException("Ticket release rate must be greater than 0");
            setTicketReleaseRate(ticketReleaseRate);

            System.out.print("Enter customer retrieval rate in seconds: ");
            int customerRetrievalRate = Integer.parseInt(scanner.nextLine());
            if (customerRetrievalRate <= 0)
                throw new IllegalArgumentException("core.com.w2051922.ticketing.Customer retrieval rate must be greater than 0");
            setCustomerRetrievalRate(customerRetrievalRate);

            System.out.print("Enter max ticket capacity: ");
            int maxTicketCapacity = Integer.parseInt(scanner.nextLine());
            if (maxTicketCapacity <= 0)
                throw new IllegalArgumentException("Max ticket capacity must be greater than 0");
            setMaxTicketCapacity(maxTicketCapacity);

            System.out.println("Configuration successfully completed");

        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter valid numbers");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void startSystem() {
        if (!isRunning) {
            isRunning = true;
            System.out.println("\nSystem started with following configuration:");
            System.out.println("Total tickets: " + getTotalTickets());
            System.out.println("Ticket release rate: " + getTicketReleaseRate() + " seconds");
            System.out.println("core.com.w2051922.ticketing.Customer retrieval rate: " + getCustomerRetrievalRate() + " seconds");
            System.out.println("Max ticket capacity: " + getMaxTicketCapacity());
        } else System.out.println("System is already running!");
    }

    private void stopSystem() {
        if (isRunning) {
            isRunning = false;
            System.out.println("\nSystem stopped!");
        } else System.out.println("System is not running!");
    }

    private void showStatus() {
        System.out.println("\n===== System status =====");
        if (isRunning) {
            System.out.println("Status: Running");
            System.out.println("Total tickets: " + getTotalTickets());
            System.out.println("Ticket release rate: " + getTicketReleaseRate() + " seconds");
            System.out.println("core.com.w2051922.ticketing.Customer retrieval rate: " + getCustomerRetrievalRate() + " seconds");
            System.out.println("Max ticket capacity: " + getMaxTicketCapacity());
        } else System.out.println("Status: Stopped");
    }
}
