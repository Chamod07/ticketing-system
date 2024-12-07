package com.chamod.ticketingbackend.service;

public interface SystemService {

    void startSystem();

    void pauseSystem();

    void resumeSystem();

    void stopSystem();

    boolean isRunning();
}
