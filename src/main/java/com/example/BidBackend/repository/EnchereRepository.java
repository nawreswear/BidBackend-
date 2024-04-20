package com.example.BidBackend.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.BidBackend.model.Enchere;

@Repository
public interface EnchereRepository extends JpaRepository<Enchere,Long>{
		@EntityGraph(attributePaths = {"articles"})
		Optional<Enchere> findById(Long id);
	    List<Enchere> findByArticlesId(Long articleId);
	    List<Enchere> findByDateFinAfter(Date date);

	    List<Enchere> findByDateDebutBefore(Date date);

	    List<Enchere> findByDateDebutBetween(Date dateDebut1, Date dateDebut2);

	@Query("SELECT e FROM Enchere e LEFT JOIN FETCH e.articles")
	List<Enchere> findAllWithArticles();
}
