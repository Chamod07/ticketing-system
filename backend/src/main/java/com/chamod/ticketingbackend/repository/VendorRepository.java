package com.chamod.ticketingbackend.repository;

import com.chamod.ticketingbackend.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Vendor entities.
 * Extends JpaRepository to provide CRUD operations for Vendor entities.
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {}