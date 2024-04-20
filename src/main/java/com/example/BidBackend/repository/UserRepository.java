package com.example.BidBackend.repository;

import com.example.BidBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByNom(String nom);

  // Optional<User> findById(Long userId);
        Optional<User> findById(Long id);
}

