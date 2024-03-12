package com.example.BidBackend.model;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Article {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    private String titre;
    private String photo;
    private String description;
    private int quantiter;
    private double prix;
    private boolean Livrable=false;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "enchere_id")
    private Enchere enchere;

    @ManyToOne
    @JoinColumn(name = "vendeur_id")
    private Vendeur vendeur;
}