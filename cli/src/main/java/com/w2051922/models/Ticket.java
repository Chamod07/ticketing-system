package com.w2051922.models;

import java.util.UUID;

public class Ticket {
    private final int id;

    public Ticket() {
        this.id = Integer.parseInt(UUID.randomUUID().toString());;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                '}';
    }
}
