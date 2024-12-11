package com.chamod.ticketingbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a Vendor.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vendor")
public class Vendor {

    /**
     * Unique identifier for the vendor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long vendorId;

    /**
     * Timestamp of the vendor record.
     */
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    /**
     * Number of tickets released per interval.
     */
    @Column(name = "tickets_per_release")
    private int ticketsPerRelease;

    /**
     * Interval between ticket releases.
     */
    @Column(name = "release_interval")
    private int releaseInterval;
}