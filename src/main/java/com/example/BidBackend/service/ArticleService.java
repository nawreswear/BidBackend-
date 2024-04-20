package com.example.BidBackend.service;

import com.example.BidBackend.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> getArticleById(Long id);
    Article createArticle(Article article);
    Article updateArticle(Long id, Article newArticle);
    void deleteArticle(Long id);
    List<Article> getAllArticles();
    void addPrixVenteForArticle(Long articleId, double prixvente, Article newArticle);
    //  List<Article> getAvailableArticles();
    Article createArticlee(Article article, Long vendeurId) ;
    List<Article> getArticlesByCategoryId(Long categoryId);
    List<Article> getArticlesByEnchereId(Long enchereId);
}
