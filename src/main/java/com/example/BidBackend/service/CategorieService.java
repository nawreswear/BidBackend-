package com.example.BidBackend.service;

import com.example.BidBackend.model.Categorie;

import java.util.List;

public interface CategorieService {
      Categorie addCategorie(Categorie cat);
     List<Categorie> addListCategorie(List<Categorie> listCategorie);
      String deleteCategorie(long id);
      Categorie getcategorieById(long id);
     Categorie updateCategorie(Categorie cat, long id);
     Categorie findByNom(String nom);
     List<Categorie> getallcategoriess();
}