package com.chamod.ticketingsystembackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "price")
    private double price;
}