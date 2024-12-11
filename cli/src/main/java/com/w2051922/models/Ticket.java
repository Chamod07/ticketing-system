package com.w2051922.models;

import java.util.UUID;

/**
 * Represents a Ticket with a unique identifier.
 */
public class Ticket {
    private final String id;

    /**
     * Constructs a new Ticket with a unique identifier.
     */
    public Ticket() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                '}';
    }
}
