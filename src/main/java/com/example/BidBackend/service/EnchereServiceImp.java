package com.example.BidBackend.service;

import java.util.*;

import com.example.BidBackend.model.*;
import com.example.BidBackend.repository.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class EnchereServiceImp implements EnchereService {

	@Autowired
	private EnchereRepository enchereRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private Part_EnRepository partEnRepository;

	@Autowired
	private Part_EnRepository participationEnchereRepository;
	@Override
	public Long getPartenIdByEnchere(Long enchereId) {
		// Recherchez l'enchère par ID
		Enchere enchere = enchereRepository.findById(enchereId)
				.orElseThrow(() -> new EntityNotFoundException("Enchère non trouvée"));

		// Récupérez l'ID du Part_En associé à l'enchère
		Long partenId = enchere.getParten().getId();

		return partenId;
	}
	@Override
	@Transactional
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
	public Void deleteEnchere(Long id) {
		Enchere enchere = enchereRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Enchere not found with id: " + id));

		// Dissocier les articles associés
		for (Article article : enchere.getArticles()) {
			article.setEnchere(null); // Mettre à jour l'ID de l'enchère dans chaque article à null
		}

		// Enregistrer les modifications des articles
		for (Article article : enchere.getArticles()) {
			articleRepository.save(article);
		}

		enchereRepository.delete(enchere); // Supprimer l'enchère
		return null;
	}

	@Override
	@Transactional
	public List<Enchere> getAllEncheres() {
		List<Enchere> encheres = enchereRepository.findAllWithParten(); // Assurez-vous que cette méthode charge également les partenaires avec leurs utilisateurs
		for (Enchere enchere : encheres) {
			enchere.getParten().getUsers().size(); // Charger explicitement les utilisateurs associés à chaque partenaire
		}
		return encheres;
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
	@Transactional
	public Enchere updateEnchere(Long id, Long adminId, Date dateDebut, Date dateFin, Long partenId) {
		// Récupérer l'enchère existante depuis la base de données avec ses associations
		Enchere existingEnchere = enchereRepository.findByIdWithAssociations(id)
				.orElseThrow(() -> new EntityNotFoundException("Enchere not found with id: " + id));

		// Charger explicitement les données de Part_En avec les utilisateurs en utilisant l'initialisation préalable (eager loading)
		Part_En parten = partEnRepository.findById(partenId)
				.orElseThrow(() -> new EntityNotFoundException("Parten not found with id: " + partenId));

		// Charger explicitement les données de Admin
		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminId));

		// Mettre à jour les champs de l'enchère
		existingEnchere.setAdmin(admin);
		existingEnchere.setDateDebut(dateDebut);
		existingEnchere.setDateFin(dateFin);
		existingEnchere.setParten(parten);

		// Enregistrer les modifications dans la base de données
		Enchere updatedEnchere = enchereRepository.save(existingEnchere);

		return updatedEnchere;
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
