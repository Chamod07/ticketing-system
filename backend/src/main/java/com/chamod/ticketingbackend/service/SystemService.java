package com.chamod.ticketingbackend.service;

/**
 * Interface for system operations.
 */
public interface SystemService {

    /**
     * Starts the system.
     *
     * @return a message indicating the system has started.
     */
    String startSystem();

    /**
     * Pauses the system.
     *
     * @return a message indicating the system has paused.
     */
    String pauseSystem();

    /**
     * Resumes the system.
     *
     * @return a message indicating the system has resumed.
     */
    String resumeSystem();

    /**
     * Stops the system.
     *
     * @return a message indicating the system has stopped.
     */
    String stopSystem();

    /**
     * Gets the current state of the system.
     *
     * @return the current state of the system.
     */
    String getState();
}