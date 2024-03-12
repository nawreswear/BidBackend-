package com.example.BidBackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BidBackend.model.Vendeur;

@Repository
public interface VendeurRepository extends JpaRepository<Vendeur,Long>{
	 List<Vendeur> findByEncheresId(Long enchereId);
}
