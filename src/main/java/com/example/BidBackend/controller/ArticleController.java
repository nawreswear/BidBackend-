package com.example.BidBackend.controller;
import com.example.BidBackend.model.*;
import com.example.BidBackend.repository.CategorieRepository;
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


import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    UserDetailsServiceImpl userService;
    @Autowired
    VendeurServiceImp vendeurService;

    @Autowired
    VendeurRepository vendeurRepository;
    @GetMapping("/getAll")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
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