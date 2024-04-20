package com.example.BidBackend.repository;

import com.example.BidBackend.model.DemandeVendeur;
import com.example.BidBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DemandeVendeurRepository extends JpaRepository<DemandeVendeur, Long> {
    Optional<DemandeVendeur> findById(Long id);
    DemandeVendeur findByUser(User user);
}
