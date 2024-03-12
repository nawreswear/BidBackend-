package com.example.BidBackend.model;


import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Vendeur extends User{
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

private String nomsociete;
    private String marque ;

    @OneToMany(mappedBy = "vendeur", cascade = CascadeType.ALL)
    private List<Article> articles;
    
}
