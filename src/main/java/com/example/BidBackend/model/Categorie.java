package com.example.BidBackend.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String image;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Article> articles;


    public Categorie() {
        this.articles = new ArrayList<>();
    }

    public Categorie(Long id) {
        this.id = id;
    }
}
