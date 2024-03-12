package com.example.BidBackend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BidBackend.model.Enchere;

@Repository
public interface EnchereRepository extends JpaRepository<Enchere,Long>{



	List<Enchere> findByArticlesId(Long articleId);
	    List<Enchere> findByDateFinAfter(Date date);

	    List<Enchere> findByDateDebutBefore(Date date);

	    List<Enchere> findByDateDebutBetween(Date dateDebut1, Date dateDebut2);
}
