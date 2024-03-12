package com.example.BidBackend.model;
import java.util.List;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Categorie {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY

    )
    private long id;
    private String nom;
    private String description;
    
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
    private List<Article> articles;
}