package com.chamod.ticketingbackend.repository;

import com.chamod.ticketingbackend.entity.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SystemConfigRepository extends JpaRepository<SystemConfiguration, Long> {
}
