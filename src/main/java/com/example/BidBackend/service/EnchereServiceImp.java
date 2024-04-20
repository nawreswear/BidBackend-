package com.example.BidBackend.service;

import java.util.*;

import com.example.BidBackend.model.Part_En;
import com.example.BidBackend.model.User;
import com.example.BidBackend.repository.AdminRepository;
import com.example.BidBackend.repository.Part_EnRepository;
import com.example.BidBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BidBackend.model.Enchere;
import com.example.BidBackend.repository.EnchereRepository;

@Service
public class EnchereServiceImp implements EnchereService {

	@Autowired
	private EnchereRepository enchereRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Part_EnRepository participationEnchereRepository;
	@Override
	public String participerEnchere(Long userId, Long enchereId) {
		// Récupérer l'utilisateur existant depuis la base de données
		User utilisateur = userRepository.findById(userId).orElse(null);
		if (utilisateur == null) {
			return "Utilisateur non trouvé";
		}

		// Récupérer l'enchère spécifique depuis la base de données
		Enchere enchere = enchereRepository.findById(enchereId).orElse(null);
		if (enchere == null) {
			return "Enchère non trouvée";
		}

		// Créer une nouvelle Part_En si nécessaire et associer l'utilisateur à l'enchère
		Part_En partEn = utilisateur.getParten();
		if (partEn == null) {
			partEn = new Part_En();
			partEn.setUsers(new ArrayList<>());
			utilisateur.setParten(partEn);
		}

		// Ajouter l'utilisateur à la liste des participants
		partEn.getUsers().add(utilisateur);

		// Ajouter l'enchère à la liste des enchères auxquelles l'utilisateur participe
		partEn.getEncheres().add(enchere);

		// Sauvegarder les modifications dans la base de données
		userRepository.save(utilisateur);

		return "Utilisateur participé avec succès à l'enchère spécifique";
	}
	@Override
	public Enchere save(Enchere enchere) {
		return enchereRepository.save(enchere);
	}
	@Override
	public String deleteEnchere(long id) {
		// Suppression de l'enchère par ID
		enchereRepository.deleteById(id);
		return "Enchere deleted successfully!";
	}

	@Override
	public List<Enchere> getAllEncheres() {
		// Récupération de toutes les enchères
		return enchereRepository.findAll();
	}

	@Override
	public List<Enchere> getAllEncheresWithArticles() {
		List<Enchere> enchereList = enchereRepository.findAll();

		for (Enchere enchere : enchereList) {
			enchere.getArticles().size(); // Cela forcera le chargement des articles
		}

		return enchereList;
	}
	@Override
	public Enchere updateEnchere(Long id, Enchere newEnchere) {
		// Mise à jour de l'enchère
		Optional<Enchere> optionalExistingEnchere = enchereRepository.findById(id);
		if (optionalExistingEnchere.isPresent()) {
			Enchere existingEnchere = optionalExistingEnchere.get();
			existingEnchere.setDateDebut(newEnchere.getDateDebut());
			existingEnchere.setDateFin(newEnchere.getDateFin());
			existingEnchere.setArticles(newEnchere.getArticles());
			existingEnchere.setParten(newEnchere.getParten());
			existingEnchere.setAdmin(newEnchere.getAdmin());
			return enchereRepository.save(existingEnchere);
		} else {
			return null;
		}
	}

	@Override
	public Optional<Enchere> getEnchereById(Long id) {
		// Récupérer l'enchère par ID
		return enchereRepository.findById(id);
	}

	@Override
	public List<Enchere> getEncheresByArticleId(Long articleId) {
		// Récupérer les enchères par ID d'article
		return enchereRepository.findByArticlesId(articleId);
	}

	@Override
	public List<Enchere> getEncheresByDateFinAfter(Date date) {
		// Récupérer les enchères avec une date de fin après une certaine date
		return enchereRepository.findByDateFinAfter(date);
	}

	@Override
	public List<Enchere> getEncheresByDateDebutBefore(Date date) {
		// Récupérer les enchères avec une date de début avant une certaine date
		return enchereRepository.findByDateDebutBefore(date);
	}

	@Override
	public List<Enchere> getEncheresByDateDebutBetween(Date dateDebut1, Date dateDebut2) {
		// Récupérer les enchères avec une date de début entre deux dates
		return enchereRepository.findByDateDebutBetween(dateDebut1, dateDebut2);
	}
}
