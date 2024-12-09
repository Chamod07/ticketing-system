package com.chamod.ticketingbackend.service;

public interface SystemService {

    String startSystem();

    String pauseSystem();

    String resumeSystem();

    String stopSystem();

    boolean isRunning();
}
