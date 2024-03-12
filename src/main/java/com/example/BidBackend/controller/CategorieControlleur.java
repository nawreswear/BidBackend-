package com.example.BidBackend.controller;
import com.example.BidBackend.model.Categorie;
import com.example.BidBackend.service.CategorieServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class CategorieControlleur {

    @Autowired
    CategorieServiceImp categorieserv;
    @PostMapping(value="/addCategorie")
    public Categorie addCategorie(@RequestBody Categorie cat) {

        return categorieserv.addCategorie(cat);
    }
    @PostMapping(value="/addListCategorie")
    public List<Categorie> addlistclient(@RequestBody List<Categorie> clientlist) {
        return categorieserv.addListCategorie(clientlist);
    }
    @PutMapping(value="/updateCategorie/{id}")
    public Categorie Updateclient(@RequestBody Categorie cl,@PathVariable Long idClient)
    {
        return categorieserv.updateCategorie(cl,idClient);
    }
    @DeleteMapping(value="/deleteCategorie/{id}")
    public String deleteCategorie(@PathVariable Long id)
    {
        return
                categorieserv.deleteCategorie(id);
    }
    @GetMapping(value="/findByNom/{nom}")
    public Categorie findByNom(@PathVariable String nom)
    {

        return categorieserv.findByNom(nom);
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
}