package com.example.BidBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Enchere extends BaseEntity {

    private Date dateFin;
    private Date dateDebut;

    @JsonIgnore
    @OneToMany(mappedBy = "enchere", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();


    @JsonIgnoreProperties({"enchere"})
    @OneToMany(mappedBy = "enchere", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Part_En> parten = new ArrayList<>();

    @JsonIgnoreProperties({"admin"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private String etat = "en cours";

    public Enchere() {
    }
}
