package com.chamod.ticketingsystembackend.repository;

import com.chamod.ticketingsystembackend.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface VendorRepository extends JpaRepository<Vendor, Integer> {}
