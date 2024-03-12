package com.example.BidBackend.service;

import com.example.BidBackend.model.Article;
import com.example.BidBackend.model.Categorie;
import com.example.BidBackend.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article findById(long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.orElse(null);
    }


    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(long id,Article article) {
        Optional<Article> existingArticleOptional = articleRepository.findById(id);
        if (existingArticleOptional.isPresent()) {
            Article existingArticle = existingArticleOptional.get();
            existingArticle.setTitre(article.getTitre());
            existingArticle.setDescription(article.getDescription());
            existingArticle.setStatut(article.getStatut());
            existingArticle.setPrix(article.getPrix());
            existingArticle.setQuantiter(article.getQuantiter());
            existingArticle.setPhoto(article.getPhoto());
            existingArticle.setCategorie(article.getCategorie()); 
            existingArticle.setEnchere(article.getEnchere());

            return articleRepository.save(existingArticle);
        } else {
            return null;
        }
    }

    @Override
    public void deleteArticle(long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
    @Override
    public List<Article> getAvailableArticles() {
        return articleRepository.findByStatut("Disponible"); // filtrer les articles disponibles
    }


   

}

