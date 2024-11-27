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
        int totalTickets = scanner.nextInt();

        // Ticket release rate
        System.out.print("Enter ticket release rate (tickets per minute): ");
        int ticketReleaseRate = scanner.nextInt();

        // Ticket retrieval rate
        System.out.print("Enter customer retrieval rate (tickets per minute): ");
        int ticketRetrievalRate = scanner.nextInt();

        // Max ticket capacity
        System.out.print("Enter maximum ticket capacity: ");
        int maxTicketCapacity = scanner.nextInt();

        // create configuration
        SystemConfiguration configuration = new SystemConfiguration(totalTickets, ticketReleaseRate, ticketRetrievalRate, maxTicketCapacity);
    }




}