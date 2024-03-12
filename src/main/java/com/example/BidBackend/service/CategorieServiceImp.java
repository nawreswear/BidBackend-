package com.example.BidBackend.service;
import com.example.BidBackend.model.Categorie;
import com.example.BidBackend.repository.CategorieRepository;
//import org.apache.el.stream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategorieServiceImp implements CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    public Categorie addCategorie(Categorie cat) {

        return categorieRepository.save(cat);
    }

    public List<Categorie> addListCategorie(List<Categorie> listCategorie) {
        return categorieRepository.saveAll(listCategorie);
    }

    @Override
    public String deleteCategorie(long id) {
        categorieRepository.deleteById(id);
        return "Categorie deleted successfully!";
    }
    @Override
    public Categorie updateCategorie(Categorie cat, long id) {
        Object existingCategorieOptional = categorieRepository.findById(id);
        if (((Optional<Categorie>) existingCategorieOptional).isPresent()) {
            Categorie existingCategorie = ((Optional<Categorie>) existingCategorieOptional).get();
            existingCategorie.setNom(cat.getNom());
            existingCategorie.setDescription(cat.getDescription());
            existingCategorie.setArticles(cat.getArticles());
            return categorieRepository.save(existingCategorie);
        } else {
            return null;
        }
    }

   public Categorie getcategorieById(long id) {
        return categorieRepository.getCategorieById(id);
    }
    public Categorie findByNom(String nom) {

        return categorieRepository.findByNom(nom);
    }
    public List<Categorie> getallcategoriess() {

        return categorieRepository.findAll();
    }


}