package com.chamod.ticketingbackend.service;

public interface CustomerService {

    void addCustomer(boolean isVip);

    void removeCustomer(boolean isVip);

    void startCustomers();

    void pauseCustomers();

    void resumeCustomers();

    void stopCustomers();

    int getCustomerCount();

    int getVipCustomerCount();
}
