package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.Customer;
import com.chamod.ticketingbackend.model.SystemConfiguration;
import com.chamod.ticketingbackend.repository.CustomerRepository;
import com.chamod.ticketingbackend.service.ConfigService;
import com.chamod.ticketingbackend.service.CustomerService;
import com.chamod.ticketingbackend.service.TicketPoolService;
import com.chamod.ticketingbackend.service.runnable.CustomerRunnable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private ConfigService configService;

    private final List<Thread> customerThreads = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<CustomerRunnable> customerRunnables = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    @Override
    public void addCustomer() {
        SystemConfiguration systemConfiguration = configService.getConfiguration();

        Customer customer = new Customer();
        customer.setTicketsPerRetrieval(systemConfiguration.getCustomerRetrievalRate());
        customer.setRetrievalInterval(1);
        customer.setCreatedAt(LocalDateTime.now());

        customerRepository.save(customer);

        customers.add(customer);

        CustomerRunnable customerRunnable = new CustomerRunnable(customer, ticketPoolService);
        Thread thread = new Thread(customerRunnable);
        customerThreads.add(thread);
        customerRunnables.add(customerRunnable);

        logger.info("Customer-{} added.", customerRunnable.getCustomerId());
    }

    @Override
    public void removeCustomer() {
        if (!customers.isEmpty() && customerRepository != null) {
            int lastIndex = customers.size() - 1;

            Thread threadToRemove = customerThreads.get(lastIndex);
            threadToRemove.interrupt();

            customerThreads.remove(threadToRemove);
            customerRunnables.remove(customerRunnables.get(lastIndex));

            // Remove customer from database
            customerRepository.delete(customers.get(lastIndex));
            customers.remove(customers.get(lastIndex));
            CustomerRunnable.removeCustomerCount();

            logger.info("Customer-{} removed", customers.size()+1);
        } else {
            logger.error("No customers to remove.");
        }
    }

    @Override
    public void startCustomers() {
        if (customerThreads.isEmpty()) {
            logger.error("No customers to start.");
            return;
        }
        for (int i = 0; i < customerThreads.size(); i++) {
            Thread thread = customerThreads.get(i);
            CustomerRunnable customerRunnable = customerRunnables.get(i);

            if (thread.isAlive()) {
                thread = new Thread(customerRunnable);
                customerThreads.add(thread);
                thread.start();
                logger.info("Customer-{} successfully started.", customerRunnable.getCustomerId());
            } else {
                logger.warn("Customer-{} has already started.", customerRunnable.getCustomerId());
            }
        }
    }

    @Override
    public void pauseCustomers() {
        if (customerThreads.isEmpty()) {
            logger.warn("No customers to pause.");
            return;
        }

        for (CustomerRunnable customerRunnable : customerRunnables) {
            customerRunnable.pause();
            logger.info("Customer-{} paused.", customerRunnable.getCustomerId());
        }
    }

    @Override
    public void resumeCustomers() {
        if (customerThreads.isEmpty()) {
            logger.warn("No customers to resume.");
            return;
        }

        for (CustomerRunnable customerRunnable : customerRunnables) {
            customerRunnable.resume();
            logger.info("Customer-{} resumed.", customerRunnable.getCustomerId());
        }
    }

    @Override
    public void stopCustomers() {
        if (customerThreads.isEmpty()) {
            logger.warn("No customers to stop.");
            return;
        }
        for (int i = 0; i < customerThreads.size(); i++) {
            new CustomerRunnable(customers.get(i), ticketPoolService).stop();
            customerThreads.get(i).interrupt();
        }
        customers.clear();
        customerThreads.clear();
        customerRunnables.clear();
        CustomerRunnable.resetCustomerCount();
        configService.loadConfiguration();
        logger.info("All customers stopped.");
    }
}
