package com.example.BidBackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Part_En {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "parten", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enchere> encheres;

    @OneToMany(mappedBy = "parten", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    public Part_En() {
        this.encheres = new ArrayList<>();
        this.users = new ArrayList<>();
    }
}
