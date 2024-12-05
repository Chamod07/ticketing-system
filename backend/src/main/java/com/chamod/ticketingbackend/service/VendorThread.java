package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.TicketPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorThread {
    private volatile boolean running = true;
    private static TicketPool ticketPool = new TicketPool();
    private static final Logger logger = LogManager.getLogger(VendorThread.class);

    public void stop() {
        this.running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                ticketPool.addTickets(ticketsPerRelease, vendorId);
                Thread.sleep(releaseInterval * 1000L);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[Vendor-{}] Interrupted during operation.", vendorId);
        }
    }
}
