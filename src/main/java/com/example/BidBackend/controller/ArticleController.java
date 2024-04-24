package com.example.BidBackend.controller;
import com.example.BidBackend.model.*;
import com.example.BidBackend.repository.ArticleRepository;
import com.example.BidBackend.repository.CategorieRepository;
import com.example.BidBackend.repository.UserRepository;
import com.example.BidBackend.repository.VendeurRepository;
import com.example.BidBackend.service.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

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
    @GetMapping("/getAll")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @PutMapping("/updateArticlePrice/{enchereId}/{articleId}")
    public ResponseEntity<String> updateArticlePrice(@PathVariable Long enchereId,
                                                     @PathVariable Long articleId,
                                                     @RequestBody Map<String, Double> requestBody) {
        Double prixvente = requestBody.get("prixvente");
        if (prixvente == null) {
            return ResponseEntity.badRequest().body("Le prix de vente n'a pas été fourni dans le corps de la requête.");
        }

        try {
            String response = articleService.updateArticlePriceByEnchereAndArticle(enchereId, articleId, prixvente);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
    @PutMapping("/addPrixVenteForArticle/{articleId}/{prixvente}")
    public ResponseEntity<String> addPrixVenteForArticle(
            @PathVariable Long articleId,
            @PathVariable double prixvente) {

        // Créer un nouvel objet Article avec le nouveau prix de vente
        Article newArticle = new Article();
        newArticle.setPrixvente(prixvente);

        // Appeler le service pour mettre à jour le prix de vente de l'article
        articleService.addPrixVenteForArticle(articleId, prixvente, newArticle);

        return ResponseEntity.ok("Prix de vente mis à jour avec succès pour l'article spécifié");
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
   /*@PostMapping("/addArticlee/{userId}")
    public Article createArticlee(@Valid @RequestBody Article article, @PathVariable("userId") Long userId) {
        try {
            Article newArticle = articleService.createArticlee(article, userId); // Remplacez "articleService" par le nom de votre service
            return newArticle;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mauvaise requête", e);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ressource non trouvée", e);
        }
    }*/

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
    @PutMapping("/UpdateArticle/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article newArticle) {
        Article updatedArticle = articleService.updateArticle(id, newArticle);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/deleteArticle/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
   /* @GetMapping("/category/{id}")
    public List<Article> getArticlesByCategory(@PathVariable Long id) {
        // Vous devez d'abord obtenir la catégorie à partir de l'ID
        // puis appeler le service pour récupérer les articles de cette catégorie
        // Voici un exemple de façon de le faire:
        Categorie categorie = // Récupérez la catégorie à partir de l'ID (vous devez avoir un service pour cela)
        return articleService.getArticlesByCategory(categorie);
    }*/
}