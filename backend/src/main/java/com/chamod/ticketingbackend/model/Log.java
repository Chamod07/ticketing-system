package com.chamod.ticketingbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a log entry in the system.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "system_logs")
public class Log {

    /**
     * The unique identifier for the log entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The action that was logged.
     */
    @Column(name = "action")
    private String action;

    /**
     * The timestamp when the action occurred.
     */
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}