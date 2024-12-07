package com.chamod.ticketingbackend.service;

public interface CustomerService {

    void addCustomer();

    void removeCustomer();

    void startCustomers();

    void pauseCustomers();

    void resumeCustomers();

    void stopCustomers();
}
