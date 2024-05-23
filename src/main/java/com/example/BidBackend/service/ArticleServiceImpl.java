package com.example.BidBackend.service;

import com.example.BidBackend.model.Article;
import com.example.BidBackend.model.Categorie;
import com.example.BidBackend.model.Enchere;
import com.example.BidBackend.model.Vendeur;
import com.example.BidBackend.repository.ArticleRepository;
import com.example.BidBackend.repository.CategorieRepository;
import com.example.BidBackend.repository.EnchereRepository;
import com.example.BidBackend.repository.VendeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private VendeurRepository vendeurRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private EnchereRepository enchereRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    public List<Article> getArticlesByCategoryId(Long categoryId) {
        return articleRepository.findByCategorie_Id(categoryId);
    }
    @Override
    @Transactional
    public List<Article> getArticlesByEnchereId(Long enchereId) {
        return articleRepository.findByEnchereId(enchereId);
    }

    @Override
    @Transactional
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
    @Override
    public Vendeur getVendeurByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);
        if (article != null) {
            return article.getVendeur();
        }
        return null;
    }
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }
    // Méthode pour mettre à jour uniquement l'attribut idEnchers
    @Override
    public void updateIdEnchers(Long articleId, Long enchereId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));

        // Récupérer l'objet Enchere correspondant à l'identifiant enchereId
        Enchere enchere = enchereRepository.findById(enchereId)
                .orElseThrow(() -> new EntityNotFoundException("Enchere not found with id: " + enchereId));

        // Définir l'objet Enchere dans l'article
        article.setEnchere(enchere);
        articleRepository.save(article);
    }
    public Article createArticle(Article article) {
        Long categorieId = article.getCategorie().getId(); // Récupérer l'ID de la catégorie de l'article
        Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new EntityNotFoundException("La catégorie avec l'ID spécifié n'existe pas."));

        article.setCategorie(categorie); // Définir la catégorie sur l'article

        return articleRepository.save(article);
    }
    @Override
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
    @Override
@Transactional
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

                    // Conserver l'ancienne valeur de enchere si la nouvelle est nulle
                    if (newArticle.getEnchere() != null) {
                        article.setEnchere(newArticle.getEnchere());
                    } else {
                        article.setEnchere(article.getEnchere());
                    }

                    article.setVendeur(newArticle.getVendeur());
                    return articleRepository.save(article);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return articleRepository.save(newArticle);
                });
    }


    public Article updateArticlee(Long id, Long enchereId) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        Optional<Enchere> optionalEnchere = enchereRepository.findById(enchereId);

        if (optionalArticle.isPresent() && optionalEnchere.isPresent()) {
            Article article = optionalArticle.get();
            Enchere enchere = optionalEnchere.get();

            article.setEnchere(enchere);

            return articleRepository.save(article);
        } else {
            throw new EntityNotFoundException("Article or Enchere not found");
        }
    }


    // Méthode pour vérifier si un article appartient à un vendeur
    private boolean isArticleBelongsToVendeur(Long articleId, Long vendeurId) {
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        return articleOptional.isPresent() && articleOptional.get().getVendeur().getId().equals(vendeurId);
    }
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}