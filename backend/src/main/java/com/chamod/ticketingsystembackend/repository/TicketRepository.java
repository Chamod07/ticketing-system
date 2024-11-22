package com.chamod.ticketingsystembackend.repository;

import com.chamod.ticketingsystembackend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // We inherit basic CRUD methods here, such as save(), findAll(), findById(), deleteById(), etc.
}
