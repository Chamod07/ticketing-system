package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.Log;
import com.chamod.ticketingbackend.repository.LogRepository;
import com.chamod.ticketingbackend.service.LogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class LogServiceImpl implements LogService {

    private final LinkedList<Log> logs = new LinkedList<>();

    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void addLog(String message) {
        Log log = new Log();
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

    @Override
    public List<Log> getLogs() {
        synchronized (logs) {
            return new LinkedList<>(logs);
        }
    }

}
