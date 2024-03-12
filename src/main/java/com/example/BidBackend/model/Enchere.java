package com.example.BidBackend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Enchere {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY

    )
    private long id;

    private Date dateFin;
    private Date dateDebut;

    @OneToMany(mappedBy = "enchere", cascade = CascadeType.ALL)
    private List<Article> articles;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "Admin_id")
    private Admin admin;

}
