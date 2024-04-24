package com.example.BidBackend.controller;

import com.example.BidBackend.model.DemandeVendeur;
import com.example.BidBackend.model.User;
import com.example.BidBackend.repository.DemandeVendeurRepository;
import com.example.BidBackend.service.DemandeVendeurService;
import com.example.BidBackend.service.DemandeVendeurServiceImp;
import com.example.BidBackend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/demandesvendeurs")
public class DemandeVendeurController {
    @Autowired
    UserDetailsServiceImpl userService;
    @Autowired
    private DemandeVendeurServiceImp demandeVendeurService;
    @Autowired
    private DemandeVendeurRepository demandeVendeurRepository;
    // Endpoint pour créer une nouvelle demande vendeur
    @PostMapping("/create")
    public ResponseEntity<?> createDemandeVendeur(@RequestBody DemandeVendeur demandeVendeur, @RequestParam Long userId) {
        try {
            DemandeVendeur createdDemandeVendeur = demandeVendeurService.createDemandeVendeur(demandeVendeur, userId);
            return new ResponseEntity<>(createdDemandeVendeur, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Une erreur s'est produite lors de la création de la demande de vendeur.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<DemandeVendeur>> getAllDemandeVendeurs() {
        try {
            List<DemandeVendeur> demandeVendeurs = demandeVendeurService.getAllDemandeVendeurs();
            return ResponseEntity.ok(demandeVendeurs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
 /* @GetMapping("/all")
    public ResponseEntity<List<DemandeVendeur>> getAllDemandeVendeurs() {
        try {
            List<DemandeVendeur> demandeVendeurs = demandeVendeurService.getAllDemandeVendeurs();
            return ResponseEntity.ok(demandeVendeurs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/


    // Endpoint pour récupérer une demande vendeur par son ID
   /* @GetMapping("/getDemandeVendeurById/{id}")
    public ResponseEntity<DemandeVendeur> getDemandeVendeurById(@PathVariable("id") Long id) {
        Optional<DemandeVendeur> demandeVendeur = demandeVendeurService.getDemandeVendeurById(id);
        return demandeVendeur.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/
    @PutMapping("/{id}/etat")
    public ResponseEntity<Void> updateDemandeEtat(@PathVariable("id") Long id,
                                                  @RequestParam("nouvelEtat") boolean nouvelEtat) {
        DemandeVendeur demandeExistante = demandeVendeurService.findById(id);

        if (demandeExistante == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Mettez à jour l'état de la demande avec la nouvelle valeur passée en paramètre
        demandeExistante.setEtatdem(nouvelEtat);

        // Enregistrez la demande mise à jour dans la base de données
        demandeVendeurRepository.save(demandeExistante);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemandeVendeur(@PathVariable("id") Long id) {
        demandeVendeurService.deleteDemandeVendeur(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // Endpoint pour mettre à jour une demande vendeur
  /*  @PutMapping("/{id}")
    public ResponseEntity<DemandeVendeur> updateDemandeVendeur(@PathVariable("id") Long id,
                                                               @RequestBody DemandeVendeur updatedDemandeVendeur) {
        DemandeVendeur demandeVendeur = demandeVendeurService.updateDemandeVendeur(id, updatedDemandeVendeur);
        if (demandeVendeur != null) {
            return new ResponseEntity<>(demandeVendeur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    // Endpoint pour supprimer une demande vendeur par son ID

}
