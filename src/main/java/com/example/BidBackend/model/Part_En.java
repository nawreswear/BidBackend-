package com.example.BidBackend.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","users"})
public class Part_En {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "parten", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Enchere> encheres;

    @OneToMany(mappedBy = "parten", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;


    public Part_En() {
        this.encheres = new ArrayList<>();
        this.users = new ArrayList<>();
    }
    public List<User> getUsers() {
        if (!Hibernate.isInitialized(users)) {
            Hibernate.initialize(users);
        }
        return new ArrayList<>(users);
    }
}