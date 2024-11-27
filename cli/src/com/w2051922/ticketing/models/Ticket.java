package com.w2051922.ticketing.models;

public class Ticket {
    private final int id;

    public Ticket(int id) {
        this.id = id;
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
