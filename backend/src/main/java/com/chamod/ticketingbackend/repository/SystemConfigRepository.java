package com.chamod.ticketingbackend.repository;

import com.chamod.ticketingbackend.model.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for SystemConfiguration entities.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
@EnableJpaRepositories
public interface SystemConfigRepository extends JpaRepository<SystemConfiguration, Long> {

    /**
     * Finds the most recently added SystemConfiguration entity.
     *
     * @return the latest SystemConfiguration entity
     */
    SystemConfiguration findFirstByOrderByIdDesc();
}