package com.example.BidBackend.service;

import com.example.BidBackend.model.DemandeVendeur;

import java.util.List;
import java.util.Optional;

public interface DemandeVendeurService {
    DemandeVendeur createDemandeVendeur(DemandeVendeur demandeVendeur, Long userId);
    List<DemandeVendeur> getAllDemandeVendeurs();
    //Optional<DemandeVendeur> getDemandeVendeurById(Long id);
   // DemandeVendeur updateDemandeVendeur(Long id, DemandeVendeur updatedDemandeVendeur);
    void deleteDemandeVendeur(Long id);
    void updateDemandeEtat(Long id, boolean nouvelEtat);
    DemandeVendeur findById(Long id);;
}
