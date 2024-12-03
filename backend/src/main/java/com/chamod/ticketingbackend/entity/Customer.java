package com.chamod.ticketingbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "priority")
    private Boolean priority;

    @Column(name = "retrieval_rate")
    private int retrievalRate;

    @Column(name = "retrieval_interval")
    private int retrievalInterval;

    @Column(name = "tickets_purchased")
    private Integer ticketsPurchased = 0;
}
