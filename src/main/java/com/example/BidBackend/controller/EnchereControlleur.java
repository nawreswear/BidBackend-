package com.example.BidBackend.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.BidBackend.model.*;
import com.example.BidBackend.repository.EnchereRepository;
import com.example.BidBackend.service.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/enchere")
@CrossOrigin(origins = "http://localhost:4200")
public class EnchereControlleur {
	@Autowired
	EnchereService enchereService;
	@Autowired
	EnchereRepository enchererepository;
	@Autowired
	UserDetailsServiceImpl userService;
	@Autowired
	AdminService adminService;
	@Autowired
	ArticleService articleService;
	@Autowired
	Part_EnService partEnService;

	@GetMapping("/{enchereId}/articles")
	public ResponseEntity<List<Article>> getArticlesForEnchere(@PathVariable Long enchereId) {
		List<Article> articles = articleService.getArticlesByEnchereId(enchereId);
		return new ResponseEntity<>(articles, HttpStatus.OK);
	}
	@PostMapping("/{userId}/participate/{enchereId}")
	@Transactional
	public ResponseEntity<Object> participateInEnchere(@PathVariable("userId") Long userId, @PathVariable("enchereId") Long enchereId) {
		// Récupérer l'utilisateur depuis la base de données
		User utilisateur = userService.findById(userId);
		if (utilisateur == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
		}

		// Récupérer l'enchère depuis la base de données
		Optional<Enchere> enchereOptional = enchereService.getEnchereById(enchereId);
		if (enchereOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enchère non trouvée");
		}
		Enchere enchere = enchereOptional.get();

		// Vérifier si l'utilisateur participe déjà à cette enchère
		boolean isAlreadyParticipating = enchere.getParten().stream()
				.anyMatch(partEn -> partEn.getUser().getId().equals(userId));
		if (isAlreadyParticipating) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("L'utilisateur participe déjà à cette enchère");
		}

		// Appeler le service de participation
		String result = enchereService.participerEnchere(userId, enchereId);
		return ResponseEntity.ok(result);
	}


	@GetMapping("/api/{nomuser}")
	public ResponseEntity<Object> findUserIdByNom(@PathVariable String nomuser) {
		try {
			Long userId = userService.findUserIdByNom(nomuser);
			return ResponseEntity.ok(userId);
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé avec le nom : " + nomuser);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la recherche de l'utilisateur.");
		}
	}
	@PostMapping("/addenchere")
	public ResponseEntity<Object> addEnchere(@RequestBody Enchere request) {
		if (request.getParten() == null || request.getAdmin() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'enchère doit contenir un utilisateur et un administrateur valides.");
		}
		// Récupérez la liste d'articles directement à partir de la requête
		List<Article> existingArticles = request.getArticles();
		// Récupérez l'utilisateur et l'administrateur à partir de la requête
		List<Part_En> partEnList = request.getParten();
		Admin admin = request.getAdmin();
		// Créez une nouvelle enchère à partir de la requête
		Enchere enchere = new Enchere();
		enchere.setDateDebut(request.getDateDebut());
		enchere.setDateFin(request.getDateFin());
		enchere.setArticles(existingArticles);
		enchere.setAdmin(admin);
		Enchere savedEnchere = enchereService.save(enchere);
		return ResponseEntity.ok(savedEnchere);
	}

	@PostMapping("/UpdateEnchere/{id}")
	@Transactional
	public ResponseEntity<Enchere> updateEnchere(@PathVariable Long id, @RequestBody Enchere updatedEnchere) {
		// Vérifiez si l'ID de l'enchère à mettre à jour est valide
		if (id == null || id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		// Vérifiez si les données de l'enchère à mettre à jour sont valides
		if (updatedEnchere == null || updatedEnchere.getDateDebut() == null || updatedEnchere.getDateFin() == null
				|| updatedEnchere.getParten() == null || updatedEnchere.getAdmin() == null) {
			return ResponseEntity.badRequest().build();
		}

		// Récupérez l'enchère à mettre à jour depuis la base de données
		Optional<Enchere> existingEnchereOptional = enchererepository.findByIdWithParten(id);
		if (!existingEnchereOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Enchere existingEnchere = existingEnchereOptional.get();
		existingEnchere.getParten().forEach(partEn -> partEn.getUser().getNom());
		existingEnchere.setDateDebut(updatedEnchere.getDateDebut());
		existingEnchere.setDateFin(updatedEnchere.getDateFin());
		existingEnchere.setParten(updatedEnchere.getParten());
		existingEnchere.setAdmin(updatedEnchere.getAdmin());
		existingEnchere.setArticles(updatedEnchere.getArticles());
		Enchere updatedEnchereEntity = enchererepository.save(existingEnchere);

		return ResponseEntity.ok(updatedEnchereEntity);
	}
    @DeleteMapping("/deleteEnchere/{id}")
	public Void deleteEnchere(@PathVariable long id)
	{
		return enchereService.deleteEnchere(id);
	}
	@GetMapping(value="/getallEncheress")
	@Transactional
	public List<Enchere> getallEncheress() {
		return enchereService.getAllEncheres();

	}
	@GetMapping("/getAllEncheres")
	@Transactional
	public ResponseEntity<List<Enchere>> getAllEncheres() {
		List<Enchere> encheres = enchereService.getAllEncheres();

		// Charger explicitement la collection partens pour chaque Enchere
		for (Enchere enchere : encheres) {
			Hibernate.initialize(enchere.getAdmin().getPartens());
		}

		return new ResponseEntity<>(encheres, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	 public Optional<Enchere> getEnchereById(@PathVariable Long id) {
		return enchereService.getEnchereById(id);
	    }


		@GetMapping("/article/{articleId}")
	    public List<Enchere> getEncheresByArticleId(@PathVariable Long articleId) {
	        return enchereService.getEncheresByArticleId(articleId);
	    }



	    @GetMapping("/datefin/after/{date}")
	    public List<Enchere> getEncheresByDateFinAfter(@PathVariable Date date) {
	        return enchereService.getEncheresByDateFinAfter(date);
	    }

	    @GetMapping("/datedebut/before/{date}")
	    public List<Enchere> getEncheresByDateDebutBefore(@PathVariable Date date) {
	        return enchereService.getEncheresByDateDebutBefore(date);
	    }

	    @GetMapping("/datedebut/between/{dateDebut1}/{dateDebut2}")
	    public List<Enchere> getEncheresByDateDebutBetween(@PathVariable Date dateDebut1, @PathVariable Date dateDebut2) {
	        return enchereService.getEncheresByDateDebutBetween(dateDebut1, dateDebut2);
	    }
}
