package com.example.BidBackend.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Part_En extends BaseEntity {

    private String etat = " ";
    private double prixproposer = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enchere_id", nullable = false)
    @JsonIgnore
    private Enchere enchere;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUserId(Long userId) {

    }
}
