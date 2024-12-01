package com.chamod.ticketingsystembackend.repository;

import com.chamod.ticketingsystembackend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {}
