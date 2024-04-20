package com.example.BidBackend.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;

    @Column(length = 1000)
    private String photo;
    private String description;
    private long quantiter;
    private double prix;
    private boolean livrable = false;
    private double prixvente;
    private String statut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enchere_id")
    private Enchere enchere;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendeur_id", nullable = true)
    private Vendeur vendeur;

    public Article( String titre, String photo, String description, long quantiter, double prix, boolean livrable, String statut, Categorie categorie) {
        this.titre = titre;
        this.photo = photo;
        this.description = description;
        this.quantiter = quantiter;
        this.prix = prix;
        this.livrable = livrable;
        this.statut = statut;
        this.categorie = categorie;
    }

    public Article(String titre, String photo, String description, long quantiter, double prix, boolean livrable, String statut, Categorie categorie, Vendeur vendeur) {
        this.titre = titre;
        this.photo = photo;
        this.description = description;
        this.quantiter = quantiter;
        this.prix = prix;
        this.livrable = livrable;
        this.statut = statut;
        this.categorie = categorie;
        this.vendeur = vendeur; // Assurez-vous d'initialiser le vendeur
    }

    public boolean isLivrable() {
        return livrable;
    }

}