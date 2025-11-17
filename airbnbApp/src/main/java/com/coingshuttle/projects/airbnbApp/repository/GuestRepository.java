package com.coingshuttle.projects.airbnbApp.repository;

import com.coingshuttle.projects.airbnbApp.Entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}