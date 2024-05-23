package com.example.BidBackend.repository;

import com.example.BidBackend.model.Part_En;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Part_EnRepository extends JpaRepository<Part_En,Long> {
  /* @Query("SELECT p FROM Part_En p WHERE p.prixproposer = (SELECT MAX(p2.prixproposer) FROM Part_En p2)")
   Part_En findTopPrixProposerParten();*/
   @EntityGraph(attributePaths = "user")
   Optional<Part_En> findById(Long id);
   @Query(value = "SELECT * FROM Part_En p WHERE p.enchere_id = :enchereId ORDER BY p.prixproposer DESC LIMIT 1", nativeQuery = true)
   Part_En findTopPrixProposerPartenByEnchereId(@Param("enchereId") Long enchereId);
   Part_En findTopByEnchereIdOrderByPrixproposerDesc(Long enchereId);
    boolean existsByUser_IdAndEnchere_Id(Long userId, Long enchereId);

}

