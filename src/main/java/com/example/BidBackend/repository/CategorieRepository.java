package com.example.BidBackend.repository;

import com.example.BidBackend.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Categorie findByTitre(String titre);
    @Query("SELECT c FROM Categorie c WHERE c.id = :id")
    Categorie getCategorieById(@Param("id")Long id);
    Optional<Categorie> findById(Long id);
}