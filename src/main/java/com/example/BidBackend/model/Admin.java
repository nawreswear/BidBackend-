package com.example.BidBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class Admin extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Enchere> encheres;

    @Getter
    @Setter
    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List<DemandeVendeur> demandes;

    public Admin() {
        this.encheres = new ArrayList<>();;
        this.demandes = new ArrayList<>();;
    }
}
