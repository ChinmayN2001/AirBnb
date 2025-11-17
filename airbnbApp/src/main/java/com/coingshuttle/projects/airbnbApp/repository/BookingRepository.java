package com.coingshuttle.projects.airbnbApp.repository;

import com.coingshuttle.projects.airbnbApp.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
