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
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "retrieval_rate")
    private int retrievalRate;

    @Column(name = "retrieval_interval")
    private int retrievalInterval;
}
