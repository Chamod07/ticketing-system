package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.Log;

import java.util.List;

/**
 * Service interface for managing logs.
 */
public interface LogService {

    /**
     * Adds a log entry with the specified message.
     *
     * @param message the message to be logged
     */
    void addLog(String message);

    /**
     * Retrieves all log entries.
     *
     * @return a list of log entries
     */
    List<Log> getLogs();

    /**
     * Resets all log entries.
     */
    void resetLogs();
}