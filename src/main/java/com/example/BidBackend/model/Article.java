package com.example.BidBackend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private String statut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enchere_id")
    private Enchere enchere;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendeur_id", nullable = true)
    private Vendeur vendeur;
    public Enchere getEnchere() {
        Hibernate.initialize(enchere);
        return enchere;
    }
    public Article( String titre, String photo, String description,long quantiter, double prix, boolean livrable, String statut, Categorie categorie,Enchere enchere) {
        this.titre = titre;
        this.photo = photo;
        this.description = description;
        this.quantiter = quantiter;
        this.prix = prix;
        this.livrable = livrable;
        this.statut = statut;
        this.categorie = categorie;
        this.enchere=enchere;
    }

    public Article(String titre, String photo, String description, long quantiter, double prix,  boolean livrable, String statut, Categorie categorie, Vendeur vendeur) {
        this.titre = titre;
        this.photo = photo;
        this.description = description;
        this.quantiter = quantiter;
        this.prix = prix;
        this.livrable = livrable;
        this.statut = statut;
        this.categorie = categorie;
    }

    public boolean isLivrable() {
        return livrable;
    }

}