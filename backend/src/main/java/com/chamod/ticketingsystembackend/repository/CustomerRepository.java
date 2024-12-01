package com.chamod.ticketingsystembackend.repository;

import com.chamod.ticketingsystembackend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CustomerRepository extends JpaRepository<Customer, String> {}
