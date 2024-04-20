package com.example.BidBackend.controller;
import com.example.BidBackend.model.Article;
import com.example.BidBackend.model.Categorie;
import com.example.BidBackend.service.ArticleService;
import com.example.BidBackend.service.ArticleServiceImpl;
import com.example.BidBackend.service.CategorieServiceImp;
import com.example.BidBackend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorie")
@CrossOrigin(origins = "http://localhost:4200")
public class CategorieControlleur {

    @Autowired
    CategorieServiceImp categorieserv;
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    UserDetailsServiceImpl userService;


    @GetMapping("/{categoryId}/articles")
    public ResponseEntity<List<Article>> getArticlesForCategory(@PathVariable Long categoryId) {
        List<Article> articles = articleService.getArticlesByCategoryId(categoryId);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }


    @PostMapping(value="/addCategorie")
    public Categorie addCategorie(@RequestBody Categorie cat) {

        return categorieserv.addCategorie(cat);
    }
    @PostMapping(value="/addListCategorie")
    public List<Categorie> addlistclient(@RequestBody List<Categorie> clientlist) {
        return categorieserv.addListCategorie(clientlist);
    }
    @PutMapping(value="/updateCategorie/{id}")
    public Categorie updateCategorie(@RequestBody Categorie cl,@PathVariable Long id)
    {
        return categorieserv.updateCategorie(cl,id);
    }
    @DeleteMapping(value="/deleteCategorie/{id}")
    public String deleteCategorie(@PathVariable Long id)
    {
        return
                categorieserv.deleteCategorie(id);
    }
    @GetMapping(value="/findByTitre/{titre}")
    public Categorie findByTitre(@PathVariable String titre)
    {

        return categorieserv.findByTitre(titre);
    }
    @GetMapping(value="/getallcategories")
    public List<Categorie> getallcategories() {

        return categorieserv.getallcategoriess();
    }

    @GetMapping(value="/getcategorieById/{id}")
    public Categorie getcategorieById(@PathVariable Long id)
    {

        return categorieserv.getcategorieById(id);
    }
  /*  @GetMapping(value="/{categoryId}/articles")
    public List<Article> getArticlesForCategory(@PathVariable Long categoryId) {
        return articleService.getArticlesByCategoryId(categoryId);
    }*/
}