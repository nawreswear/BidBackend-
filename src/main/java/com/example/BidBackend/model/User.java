package com.example.BidBackend.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","demandes"})
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    private String type;

    @NotBlank
    @Size(max = 20)
    private String nom;

    @NotBlank
    @Size(max = 20)
    private String prenom;

    @NotNull(message = "Tel cannot be null")
    private Integer tel;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Min(value = 1000, message = "Postal code must be at least 1000")
    @Max(value = 9999, message = "Postal code must be at most 9999")
    private Integer codePostal;

    @Size(max = 20)
    private String pays;

    @Size(max = 20)
    private String ville;

    @Digits(integer = 10, fraction = 0)
    private Integer cin;

    private double longitude;
    private double latitude;

    private String photo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parten_id")
    private Part_En parten ;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DemandeVendeur> demandes;
    public User() {
    }
    public User(int id) {
        this.id = (long) id;
    }

}
