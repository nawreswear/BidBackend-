package com.example.BidBackend.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.BidBackend.model.Enchere;
import com.example.BidBackend.repository.EnchereRepository;

@Service
public class EnchereServiceImp implements EnchereService{

	 @Autowired
	    private EnchereRepository ER;
	@Override
	public Enchere save(Enchere e) {
		return ER.save(e);
	}

	 @Override
	    public String deleteEnchere(long id) {
	        ER.deleteById(id);
	        return "Enchere deleted successfully!";
	    }
	 
	@Override
	public List<Enchere> getAllEncheres() {
		return ER.findAll();
	}
	

	@Override
	public Enchere updateEnchere(Long id, Enchere e) {
		   Optional<Enchere> existingEnOptional = ER.findById(id);
	        if (existingEnOptional.isPresent()) {
	        	Enchere existingEn = existingEnOptional.get();
	        	existingEn.setDateDebut(e.getDateDebut());
	        	existingEn.setDateFin(e.getDateFin());

	        	existingEn.setArticles(e.getArticles());

	            return ER.save(existingEn);
	        } else {
	            return null;
	        }
	}
	public Enchere getEnchereById(Long id) {
	    Optional<Enchere> enchereOptional = ER.findById(id);
	    return enchereOptional.orElse(null);
	}
	public List<Enchere> getEncheresByArticleId(Long articleId) {
	    return ER.findByArticlesId(articleId);
	}

	public List<Enchere> getEncheresByDateFinAfter(Date date) {
	    return ER.findByDateFinAfter(date);
	}
	public List<Enchere> getEncheresByDateDebutBefore(Date date) {
	    return ER.findByDateDebutBefore(date);
	}
	public List<Enchere> getEncheresByDateDebutBetween(Date dateDebut1, Date dateDebut2) {
	    return ER.findByDateDebutBetween(dateDebut1, dateDebut2);
	}
}
