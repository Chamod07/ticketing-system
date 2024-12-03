package com.chamod.ticketingbackend.entity;

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
    private Long id;

    @Column(name = "release_rate")
    private int releaseRate;

    @Column(name = "release_interval")
    private int releaseInterval;

    @Column(name = "tickets_released")
    private Integer ticketsReleased = 0;
}
