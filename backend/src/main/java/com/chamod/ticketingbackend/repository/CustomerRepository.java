package com.chamod.ticketingbackend.repository;

import com.chamod.ticketingbackend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Customer entities.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    /**
     * Finds the first Customer with priority set to true, ordered by timestamp in descending order.
     *
     * @return the first Customer with priority true, ordered by timestamp descending.
     */
    Customer findFirstByPriorityTrueOrderByTimestampDesc();

    /**
     * Finds the first Customer with priority set to false, ordered by timestamp in descending order.
     *
     * @return the first Customer with priority false, ordered by timestamp descending.
     */
    Customer findFirstByPriorityFalseOrderByTimestampDesc();
}