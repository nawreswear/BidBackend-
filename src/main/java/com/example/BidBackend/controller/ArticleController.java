package com.example.BidBackend.controller;
import com.example.BidBackend.model.Article;
import com.example.BidBackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
@CrossOrigin(origins = "http://localhost:3000")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    
	@PostMapping(value="/addArticle")
	public Article addArticle(@RequestBody Article c){
		return articleService.save(c);
	}
	@PutMapping(value="/UpdateArticle/{idart}")
	public Article UpdateArticle(@PathVariable long idart,@RequestBody Article c) 
	{
		return articleService.updateArticle(idart,c);
	}

    @PutMapping("/updateStatut/{id}")
    public ResponseEntity<?> updateArticleStatut(@PathVariable Long id, @RequestBody Map<String, String> data) {
        String statut = data.get("statut");

        try {
            Article article = articleService.findById(id);
            article.setStatut(statut);
            articleService.save(article);
            return ResponseEntity.ok(Map.of("message", "Statut de l'article mis à jour avec succès."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erreur lors de la mise à jour du statut de l'article."));
        }
    }

    @GetMapping("/getAll")
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }


    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Long id)
    {
        return articleService.findById(id);
    }

    @DeleteMapping(value="/deleteArticle/{idart}")
	public void deleteArticle(@PathVariable long idart) 
	{
		 articleService.deleteArticle(idart);
	}
    @GetMapping("/available")
    public List<Article> getAvailableArticles() {
        return articleService.getAvailableArticles();
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