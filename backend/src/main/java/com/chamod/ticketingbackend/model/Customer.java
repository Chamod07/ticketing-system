package com.chamod.ticketingbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a Customer.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "customer")
public class Customer {

    /**
     * Unique identifier for the customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * Timestamp of the customer record.
     */
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    /**
     * Indicates if the customer has priority.
     */
    @Column(name = "priority")
    private boolean priority;

    /**
     * Number of tickets per retrieval.
     */
    @Column(name = "tickets_per_retrieval")
    private int ticketsPerRetrieval;

    /**
     * Interval between retrievals in minutes.
     */
    @Column(name = "retrieval_interval")
    private int retrievalInterval;
}