package com.coingshuttle.projects.airbnbApp.repository;

import com.coingshuttle.projects.airbnbApp.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
