package com.w2051922.models;

import java.util.UUID;

public class Ticket {
    private final String id;

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
