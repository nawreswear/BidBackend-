package com.example.BidBackend.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.BidBackend.model.Enchere;

public interface EnchereService {

	Enchere save(Enchere commande);
	Long getPartenIdByEnchere(Long enchereId);
	Void deleteEnchere(Long id);

	List<Enchere> getAllEncheres();
	Enchere updateEnchere(Long id, Long adminId, Date dateDebut, Date dateFin, Long partenId);
	Optional<Enchere> getEnchereById(Long id);

	List<Enchere> getEncheresByArticleId(Long articleId);

	List<Enchere> getEncheresByDateFinAfter(Date date);

	List<Enchere> getEncheresByDateDebutBefore(Date date);

	List<Enchere> getEncheresByDateDebutBetween(Date dateDebut1, Date dateDebut2);

	String participerEnchere(Long userId, Long enchereId);

	List<Enchere> getAllEncheresWithArticles();
}
