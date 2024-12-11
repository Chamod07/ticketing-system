package com.chamod.ticketingbackend.service.impl;

import com.chamod.ticketingbackend.model.Customer;
import com.chamod.ticketingbackend.model.SystemConfiguration;
import com.chamod.ticketingbackend.repository.CustomerRepository;
import com.chamod.ticketingbackend.service.ConfigService;
import com.chamod.ticketingbackend.service.CustomerService;
import com.chamod.ticketingbackend.service.SystemService;
import com.chamod.ticketingbackend.service.TicketPoolService;
import com.chamod.ticketingbackend.service.runnable.CustomerRunnable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private ConfigService configService;

    @Lazy
    @Autowired
    private SystemService systemService;

    private final List<Thread> customerThreads = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<CustomerRunnable> customerRunnables = new ArrayList<>();

    private final List<Thread> vipCustomerThreads = new ArrayList<>();
    private final List<Customer> vipCustomers = new ArrayList<>();
    private final List<CustomerRunnable> vipCustomerRunnables = new ArrayList<>();

    private final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    @Override
    public void addCustomer(boolean isVip) {
        SystemConfiguration systemConfiguration = configService.getConfiguration();

        if (systemConfiguration == null) {
            logger.warn("System configuration is not initialized. Cannot add new customer.");
        } else {
            Customer customer = new Customer();
            customer.setTicketsPerRetrieval(systemConfiguration.getCustomerRetrievalRate());
            customer.setRetrievalInterval(1);
            customer.setPriority(isVip);
            customer.setTimestamp(LocalDateTime.now());

            customerRepository.save(customer);

            CustomerRunnable customerRunnable = new CustomerRunnable(customer, ticketPoolService, isVip);
            Thread customerThread = new Thread(customerRunnable);

            if (isVip) {
                customerThread.setPriority(Thread.MAX_PRIORITY); // set maximum priority for the VIP thread
                vipCustomers.add(customer);
                vipCustomerThreads.add(customerThread);
                vipCustomerRunnables.add(customerRunnable);
                logger.info("[VIP Customer-{}] added.", customerRunnable.getCustomerId());
            } else {
                customers.add(customer);
                customerThreads.add(customerThread);
                customerRunnables.add(customerRunnable);
                logger.info("[Customer-{}] added.", customerRunnable.getCustomerId());
            }

            // start thread if system is running
            if (Objects.equals(systemService.getState(), "Running")) {
                customerThread.start();
                logger.info("[{}Customer-{}] started immediately as the system is running.", isVip ? "VIP " : "", customerRunnable.getCustomerId());
            }
        }
    }

    @Override
    public void removeCustomer(boolean isVip) {
        List<Customer> customerList = isVip ? vipCustomers : customers;
        List<Thread> customerThreadList = isVip ? vipCustomerThreads : customerThreads;
        List<CustomerRunnable> customerRunnableList = isVip ? vipCustomerRunnables : customerRunnables;

        if (!customerList.isEmpty() && customerRepository != null) {
            int lastIndex = customerList.size() - 1;

            Thread threadToRemove = customerThreadList.get(lastIndex);
            threadToRemove.interrupt();

            customerThreadList.remove(threadToRemove);
            customerRunnableList.remove(customerRunnableList.get(lastIndex));
            customerList.remove(lastIndex);

            // Remove customer from database
            if (isVip) {
                customerRepository.delete(customerRepository.findFirstByPriorityTrueOrderByTimestampDesc()); // remove VIP customer
            } else {
                customerRepository.delete(customerRepository.findFirstByPriorityFalseOrderByTimestampDesc()); // remove normal customer
            }

            if (isVip) {
                CustomerRunnable.removeVipCustomerCount();
            } else {
                CustomerRunnable.removeCustomerCount();
            }

            logger.info("{}Customer-{} removed", isVip ? "VIP " : "", customerList.size() + 1);
        } else {
            logger.warn("No {} customers to remove.", isVip ? "VIP" : "regular");
        }
    }

    @Override
    public void startCustomers() {
        if (customerThreads.isEmpty() && vipCustomerThreads.isEmpty()) {
            logger.warn("No customers to start.");
            return;
        }

        for (int i = 0; i < vipCustomerThreads.size(); i++) {
            Thread customerThread = vipCustomerThreads.get(i);
            CustomerRunnable customerRunnable = vipCustomerRunnables.get(i);

            if (!customerThread.isAlive()) {
                customerThread = new Thread(customerRunnable);
                vipCustomerThreads.set(i, customerThread);
                customerThread.start();
                logger.info("VIP Customer-{} started.", customerRunnable.getCustomerId());
            } else {
                logger.warn("VIP Customer-{} already started.", customerRunnable.getCustomerId());
            }
        }

        for (int i = 0; i < customerThreads.size(); i++) {
            Thread customerThread = customerThreads.get(i);
            CustomerRunnable customerRunnable = customerRunnables.get(i);

            if (!customerThread.isAlive()) {
                customerThread = new Thread(customerRunnable);
                customerThreads.set(i, customerThread);
                customerThread.start();
                logger.info("Customer-{} started.", customerRunnable.getCustomerId());
            } else {
                logger.warn("Customer-{} already started.", customerRunnable.getCustomerId());
            }
        }
    }

    @Override
    public void pauseCustomers() {
        if (customerThreads.isEmpty() && vipCustomerThreads.isEmpty()) {
            logger.warn("No customers to pause.");
            return;
        }

        for (CustomerRunnable vipCustomerRunnable : vipCustomerRunnables) {
            vipCustomerRunnable.pause();
            logger.info("VIP Customer-{} paused.", vipCustomerRunnable.getCustomerId());
        }

        for (CustomerRunnable customerRunnable : customerRunnables) {
            customerRunnable.pause();
            logger.info("Customer-{} paused.", customerRunnable.getCustomerId());
        }
    }

    @Override
    public void resumeCustomers() {
        if (customerThreads.isEmpty() && vipCustomerThreads.isEmpty()) {
            logger.warn("No customers to resume.");
            return;
        }

        for (CustomerRunnable vipCustomerRunnable : vipCustomerRunnables) {
            vipCustomerRunnable.resume();
            logger.info("VIP Customer-{} resumed.", vipCustomerRunnable.getCustomerId());
        }

        for (CustomerRunnable customerRunnable : customerRunnables) {
            customerRunnable.resume();
            logger.info("Customer-{} resumed.", customerRunnable.getCustomerId());
        }

        // start customers added while paused
        for (int i = 0; i < vipCustomerThreads.size(); i++) {
            Thread vipCustomerThread = vipCustomerThreads.get(i);
            if (!vipCustomerThread.isAlive()) {
                vipCustomerThread.start();
                logger.info("VIP Customer-{} started during system resume.", vipCustomerRunnables.get(i).getCustomerId());
            }
        }

        for (int i = 0; i < customerThreads.size(); i++) {
            Thread customerThread = customerThreads.get(i);
            if (!customerThread.isAlive()) {
                customerThread.start();
                logger.info("Customer-{} started during system resume.", customerRunnables.get(i).getCustomerId());
            }
        }
    }

    @Override
    public void stopCustomers() {
        if (customerThreads.isEmpty() && vipCustomerThreads.isEmpty()) {
            logger.warn("No customers to stop.");
            return;
        }

        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        for (Thread vipCustomerThread : vipCustomerThreads) {
            vipCustomerThread.interrupt();
        }

        customers.clear();
        customerThreads.clear();
        customerRunnables.clear();

        vipCustomers.clear();
        vipCustomerThreads.clear();
        vipCustomerRunnables.clear();

        CustomerRunnable.resetCustomerCount();
        CustomerRunnable.resetVipCustomerCount();

        configService.loadConfiguration();

        logger.info("All customers stopped.");
    }

    @Override
    public int getCustomerCount() {
        return customers.size();
    }

    @Override
    public int getVipCustomerCount() {
        return vipCustomers.size();
    }
}
