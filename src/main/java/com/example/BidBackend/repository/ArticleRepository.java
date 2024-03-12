package com.example.BidBackend.repository;
import com.example.BidBackend.model.Article;
import com.example.BidBackend.model.Categorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
	List<Article> findByCategorie(Categorie categorie);
	List<Article> findByStatut(String statut);
    Optional<Article> findById(Long id);
  
}



