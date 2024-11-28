package com.w2051922.models;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private String vendorID;
    private int ticketsPerRelease;
    private int releaseInterval;

    public Vendor(TicketPool ticketPool, String vendorID, int ticketsPerRelease, int releaseInterval) {
        this.ticketPool = ticketPool;
        this.vendorID = vendorID;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    public String getVendorID() {
        return vendorID;
    }
    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }
    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }
    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }
    public int getReleaseInterval() {
        return releaseInterval;
    }
    public void setReleaseInterval(int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ticketPool.addTicket(ticketsPerRelease); // Add tickets
                Thread.sleep(releaseInterval * 1000); // Wait for the release rate
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor interrupted.");
        }
    }

    @Override
    public String toString() {
        return vendorID + "\t" + ticketsPerRelease + "\t" + releaseInterval;
    }
}
