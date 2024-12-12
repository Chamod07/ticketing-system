package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.Log;
import com.chamod.ticketingbackend.repository.LogRepository;
import com.chamod.ticketingbackend.service.LogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service implementation for managing logs.
 */
@Service
public class LogServiceImpl implements LogService {

    private final LinkedList<Log> logs = new LinkedList<>();

    private final LogRepository logRepository;

    /**
     * Constructs a new LogServiceImpl with the specified LogRepository.
     *
     * @param logRepository the repository to use for saving logs
     */
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * Adds a new log entry with the specified message.
     *
     * @param message the message to log
     */
    @Override
    public void addLog(String user, String message) {
        Log log = new Log();
        log.setUser(user);
        log.setAction(message);
        log.setTimestamp(LocalDateTime.now());

        synchronized (logs) {
            if (logs.size() >= 50) {
                logs.remove(0); // to reduce memory usage
            }
            logs.add(log);
        }
        logRepository.save(log);
    }

    /**
     * Retrieves the list of log entries.
     *
     * @return a list of log entries
     */
    @Override
    public List<Log> getLogs() {
        synchronized (logs) {
            return new LinkedList<>(logs);
        }
    }

    /**
     * Resets the log entries by clearing the log list.
     */
    @Override
    public void resetLogs() {
        synchronized (logs) {
            logs.clear();
        }
    }
}