package com.example.decapay.repositories;

import com.example.decapay.models.User;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
}
