package com.chamod.ticketingbackend.repository;

import com.chamod.ticketingbackend.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing Log entities.
 * Extends JpaRepository to provide CRUD operations and more.
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}