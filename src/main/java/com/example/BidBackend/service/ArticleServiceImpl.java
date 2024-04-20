package com.example.BidBackend.service;

import com.example.BidBackend.model.Article;
import com.example.BidBackend.model.Categorie;
import com.example.BidBackend.model.Vendeur;
import com.example.BidBackend.repository.ArticleRepository;
import com.example.BidBackend.repository.CategorieRepository;
import com.example.BidBackend.repository.VendeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private VendeurRepository vendeurRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    public List<Article> getArticlesByCategoryId(Long categoryId) {
        return articleRepository.findByCategorie_Id(categoryId);
    }
    @Override
    public void addPrixVenteForArticle(Long articleId, double prixvente, Article newArticle) {
        // Rechercher l'article par son ID
        Optional<Article> optionalArticle = articleRepository.findById(articleId);

        optionalArticle.ifPresent(article -> {
            // Comparer le nouveau prix de vente avec le prix actuel de l'article
            if (prixvente > article.getPrixvente()) {
                // Si le nouveau prix de vente est supérieur, mettre à jour le prix de vente de l'article
                article.setPrixvente(prixvente);
                articleRepository.save(article);
            }
            // Si le nouveau prix de vente n'est pas supérieur, ne rien faire
        });
    }
    public List<Article> getArticlesByEnchereId(Long enchereId) {
        // Utilisez le repository pour récupérer les articles associés à l'enchère
        return articleRepository.findByEnchereId(enchereId);
    }
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(Article article) {
        Long categorieId = article.getCategorie().getId(); // Récupérer l'ID de la catégorie de l'article
        Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new EntityNotFoundException("La catégorie avec l'ID spécifié n'existe pas."));

        article.setCategorie(categorie); // Définir la catégorie sur l'article

        return articleRepository.save(article);
    }
    public Article createArticlee(Article article, Long vendeurId) {
        // Vérifiez si l'objet Vendeur associé à l'article est null
        if (vendeurId == null) {
            throw new IllegalArgumentException("Vendeur ID cannot be null.");
        }
        // Récupérez le vendeur de la base de données en utilisant l'identifiant
        Vendeur vendeur = vendeurRepository.findById(vendeurId)
                .orElseThrow(() -> new EntityNotFoundException("Le vendeur avec l'ID spécifié n'existe pas."));
        // Définissez le vendeur sur l'article
        article.setVendeur(vendeur);
        // Récupérez l'identifiant de la catégorie de l'article
        Long categorieId = article.getCategorie().getId();
        // Récupérez la catégorie de la base de données en utilisant l'identifiant
        Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new EntityNotFoundException("La catégorie avec l'ID spécifié n'existe pas."));
        // Définissez la catégorie sur l'article
        article.setCategorie(categorie);
        // Enregistrez l'article avec la catégorie et le vendeur mis à jour
        return articleRepository.save(article);
    }
    public Article updateArticle(Long id, Article newArticle) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitre(newArticle.getTitre());
                    article.setPhoto(newArticle.getPhoto());
                    article.setDescription(newArticle.getDescription());
                    article.setQuantiter(newArticle.getQuantiter());
                    article.setPrix(newArticle.getPrix());
                    article.setLivrable(newArticle.isLivrable());
                    article.setStatut(newArticle.getStatut());
                    article.setCategorie(newArticle.getCategorie());
                    article.setEnchere(newArticle.getEnchere());
                    article.setVendeur(newArticle.getVendeur());
                    // Mettre à jour d'autres champs si nécessaire
                    return articleRepository.save(article);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return articleRepository.save(newArticle);
                });
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}