package com.chamod.ticketingbackend.repository;

import com.chamod.ticketingbackend.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
