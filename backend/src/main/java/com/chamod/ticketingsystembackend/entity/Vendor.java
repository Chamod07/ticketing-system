package com.chamod.ticketingsystembackend.entity;

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
    private int vendorId;

    @Column(name = "release_rate")
    private int releaseRate;

    @Column(name = "release_interval")
    private int releaseInterval;
}
