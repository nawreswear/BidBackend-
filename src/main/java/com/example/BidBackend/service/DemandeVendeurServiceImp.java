package com.example.BidBackend.service;

import com.example.BidBackend.model.DemandeVendeur;
import com.example.BidBackend.model.Part_En;
import com.example.BidBackend.model.User;
import com.example.BidBackend.repository.DemandeVendeurRepository;
import com.example.BidBackend.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeVendeurServiceImp implements DemandeVendeurService{

    @Autowired
    private DemandeVendeurRepository demandeVendeurRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public DemandeVendeur createDemandeVendeur(DemandeVendeur demandeVendeur, Long userId) {
        // Assurez-vous que l'utilisateur existe en base de données
        Optional<User> userOptional = userRepository.findById(userId);

        // Vérifiez si l'utilisateur existe
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Associez l'utilisateur à la demande de vendeur
            demandeVendeur.setUser(user);

            // Maintenant, vous pouvez sauvegarder la demande de vendeur
            return demandeVendeurRepository.save(demandeVendeur);
        } else {
            // Gérer le cas où l'utilisateur n'existe pas
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId);
        }
    }
    @Override
    @Transactional
    public List<DemandeVendeur> getAllDemandeVendeurs() {
        List<DemandeVendeur> demandeVendeurs = demandeVendeurRepository.findAll();
        demandeVendeurs.forEach(demandeVendeur -> {
            Hibernate.initialize(demandeVendeur.getUser().getPartens());
        });
        return demandeVendeurs;
    }
    /* @Override
    // Méthode pour récupérer une demande vendeur par son ID
    public Optional<DemandeVendeur> getDemandeVendeurById(Long id) {
        Optional<DemandeVendeur> demandeVendeurOptional = demandeVendeurRepository.findById(id);

        demandeVendeurOptional.ifPresent(demandeVendeur -> {
            demandeVendeur.getUser().getType(); // Charger l'utilisateur pour éviter LazyInitializationException
        });

        return demandeVendeurOptional;
    }*/
    // Méthode pour mettre à jour une demande vendeur
    @Override
   public void updateDemandeEtat(Long id, boolean nouvelEtat) {
       DemandeVendeur demande = demandeVendeurRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Demande non trouvée avec l'ID : " + id));
       demande.setEtatdem(nouvelEtat); // Mettre à jour l'état de la demande
       demandeVendeurRepository.save(demande); // Enregistrer les modifications dans la base de données
   }
   @Override
    public DemandeVendeur findById(Long id) {
        return demandeVendeurRepository.findById(id)
                .orElse(null); // Retourner null si la demande n'est pas trouvée
    }

    // Méthode pour supprimer une demande vendeur par son ID
    @Override
    public void deleteDemandeVendeur(Long id) {
        demandeVendeurRepository.deleteById(id);
    }


}

