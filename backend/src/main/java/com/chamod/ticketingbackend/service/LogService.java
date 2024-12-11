package com.chamod.ticketingbackend.service;

import com.chamod.ticketingbackend.model.Log;

import java.util.List;

public interface LogService {

    void addLog(String message);

    List<Log> getLogs();
}
