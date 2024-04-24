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
   /* @Query("SELECT p FROM Part_En p LEFT JOIN FETCH p.encheres e LEFT JOIN FETCH p.users u WHERE p.id = :id")
    Part_En findPart_EnWithAssociations(@Param("id") Long id);*/
   @EntityGraph(attributePaths = "users")
   Optional<Part_En> findById(Long id);
}
