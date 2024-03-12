package com.example.BidBackend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User {

    @Size(max = 20)
    private String type ;

    @OneToMany(mappedBy = "Admin", cascade = CascadeType.ALL)
    private List<Enchere> encheres;
}
