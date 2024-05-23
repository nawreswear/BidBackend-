package com.example.BidBackend.controller;
import com.example.BidBackend.model.*;
import com.example.BidBackend.repository.ArticleRepository;
import com.example.BidBackend.repository.CategorieRepository;
import com.example.BidBackend.repository.UserRepository;
import com.example.BidBackend.repository.VendeurRepository;
import com.example.BidBackend.service.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/article")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategorieServiceImp categorieService;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    EnchereService enchereService;
    @Autowired
   ArticleRepository articleRepository;
    @Autowired
    UserDetailsServiceImpl userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VendeurServiceImp vendeurService;
    @Autowired
    Part_EnService partEnService;

    @Autowired
    VendeurRepository vendeurRepository;
    @Transactional
    @GetMapping("/getAll")
    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        List<Article> articleDTOs = new ArrayList<>();
        for (Article article : articles) {
            // Initialize the collection of Enchere objects for the article
            Hibernate.initialize(article.getEnchere());
            // Convert Article to ArticleDTO
            Article articleDTO = convertToDTO(article);
            articleDTOs.add(articleDTO);
        }

        return articleDTOs;
    }

    private Article convertToDTO(Article article) {
        Article articleDTO = new Article();
        articleDTO.setId(article.getId());
        articleDTO.setTitre(article.getTitre());
        articleDTO.setPrix(article.getPrix());
        articleDTO.setPhoto(article.getPhoto());
        articleDTO.setCategorie(article.getCategorie());
        articleDTO.setStatut(article.getStatut());
        articleDTO.setQuantiter(article.getQuantiter());
        articleDTO.setLivrable(article.isLivrable());
        articleDTO.setVendeur(article.getVendeur());

        articleDTO.setDescription(article.getDescription());
        Hibernate.initialize(article.getEnchere());
        return articleDTO;
    }

    @PutMapping("/updateIdEnchers/{articleId}/{enchereId}")
    public ResponseEntity<Void> updateIdEnchers(@PathVariable Long articleId, @PathVariable Long enchereId) {
        try {
            articleService.updateIdEnchers(articleId, enchereId);
            return ResponseEntity.ok().build(); // Retourner une réponse sans contenu en cas de succès
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retourner une réponse 404 si l'article ou l'enchère n'est pas trouvée
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retourner une réponse 500 pour les autres erreurs
        }
    }
    @PutMapping("/{id}/enchere/{enchereId}")
    public ResponseEntity<Article> updateArticlee(@PathVariable("id") Long articleId, @PathVariable("enchereId") Long enchereId) {
        Article updatedArticle = articleService.updateArticlee(articleId, enchereId);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id)
                .orElse(null); // Gérer le cas où l'article n'est pas trouvé
        if (article != null) {
            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping(value = "/addArticle")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Article createdArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }
    @PostMapping("/addArticlee/{vendeurId}")
    public ResponseEntity<Article> createArticlee(@RequestBody Article article, @PathVariable Long vendeurId) {
        // Vérifiez si l'ID du vendeur est null ou non valide
        if (vendeurId == null) {
            throw new IllegalArgumentException("Vendeur ID cannot be null.");
        }
        // Récupérez le vendeur de la base de données en utilisant l'ID du vendeur
        Vendeur vendeur = vendeurRepository.findById(vendeurId)
                .orElseThrow(() -> new EntityNotFoundException("Le vendeur avec l'ID spécifié n'existe pas."));
        // Définissez le vendeur sur l'article
        article.setVendeur(vendeur);
        // Appel de la méthode createArticlee avec l'article mis à jour
        Article createdArticle = articleService.createArticlee(article,vendeurId);
        // Retournez la réponse avec l'article créé
        return ResponseEntity.ok().body(createdArticle);
    }
    @Transactional
    @PutMapping("/UpdateArticle/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article newArticle) {
        Article updatedArticle = articleService.updateArticle(id, newArticle);
        return ResponseEntity.ok(updatedArticle);
    }
    @GetMapping("/{articleId}/vendeur")
    public ResponseEntity<Number> getVendeurByArticleId(@PathVariable Long articleId) {
        Optional<Article> article = articleService.getArticleById(articleId);
        if (article != null) {
            // Si l'article est trouvé, cherchez le vendeur
            User vendeur = article.get().getVendeur();
            if (vendeur != null) {
                // Si le vendeur est trouvé, retournez son nom
                return ResponseEntity.ok(vendeur.getId());
            } else {
                // Si le vendeur n'est pas trouvé, retournez un message approprié
                return ResponseEntity.ok(0); // Ou retournez un statut 404 avec un message approprié
            }
        } else {
            // Si l'article n'est pas trouvé, retournez un message approprié
            return ResponseEntity.ok(-1);
        }
    }
    @DeleteMapping("/deleteArticle/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}