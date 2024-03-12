package com.example.BidBackend.service;

import com.example.BidBackend.model.Article;
import com.example.BidBackend.model.Categorie;

import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface ArticleService {
    Article findById(long id);
    Article save(Article article);
    Article updateArticle(long id, Article article);
    void deleteArticle(long id);
    List<Article> getAllArticles();
    List<Article> getAvailableArticles();
   // List<Article> getArticlesByCategory(Categorie categorie);
}
