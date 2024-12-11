package com.chamod.ticketingbackend.service;

/**
 * Service interface for managing customers.
 */
public interface CustomerService {

    /**
     * Adds a customer.
     *
     * @param isVip true if the customer is a VIP, false otherwise
     */
    void addCustomer(boolean isVip);

    /**
     * Removes a customer.
     *
     * @param isVip true if the customer is a VIP, false otherwise
     */
    void removeCustomer(boolean isVip);

    /**
     * Starts processing customers.
     */
    void startCustomers();

    /**
     * Pauses processing customers.
     */
    void pauseCustomers();

    /**
     * Resumes processing customers.
     */
    void resumeCustomers();

    /**
     * Stops processing customers.
     */
    void stopCustomers();

    /**
     * Gets the total number of customers.
     *
     * @return the total number of customers
     */
    int getCustomerCount();

    /**
     * Gets the total number of VIP customers.
     *
     * @return the total number of VIP customers
     */
    int getVipCustomerCount();
}