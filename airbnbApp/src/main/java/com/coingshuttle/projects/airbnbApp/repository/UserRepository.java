package com.coingshuttle.projects.airbnbApp.repository;

import com.coingshuttle.projects.airbnbApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByEmail(String email);
}
