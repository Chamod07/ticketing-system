package com.chamod.ticketingbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "tickets_per_release")
    private int ticketsPerRelease;

    @Column(name = "release_interval")
    private int releaseInterval;
}
