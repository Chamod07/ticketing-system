package com.chamod.ticketingbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "priority")
    private boolean priority;

    @Column(name = "tickets_per_retrieval")
    private int ticketsPerRetrieval;

    @Column(name = "retrieval_interval")
    private int retrievalInterval;
}
