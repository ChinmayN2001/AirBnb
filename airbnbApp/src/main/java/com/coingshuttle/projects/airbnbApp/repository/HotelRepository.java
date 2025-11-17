package com.coingshuttle.projects.airbnbApp.repository;

import com.coingshuttle.projects.airbnbApp.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

}
