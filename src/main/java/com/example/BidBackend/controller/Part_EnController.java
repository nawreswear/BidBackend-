package com.example.BidBackend.controller;

import com.example.BidBackend.model.User;
import com.example.BidBackend.service.UserDetailsServiceImpl;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.BidBackend.model.Part_En;
import com.example.BidBackend.service.Part_EnService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/parten")
@CrossOrigin(origins = "http://localhost:4200")
public class Part_EnController {

    @Autowired
    UserDetailsServiceImpl userService;
    @Autowired
    private Part_EnService partEnService;

    @PostMapping("/addPrixVenteProposerPar/{partenId}/{Prixproposer}/{enchereId}")
    public ResponseEntity<String> addPrixVenteForArticle(
            @PathVariable Long partenId,@PathVariable double Prixproposer,@PathVariable Long enchereId) {

        // Appeler le service pour mettre à jour le prix proposé par le partenaire
        partEnService.addPrixVenteForParten(partenId,enchereId,Prixproposer);

        return ResponseEntity.ok("Prix de proposer mis à jour avec succès pour le partenaire spécifié");

    }
    @GetMapping("/getTopPrixProposerParten/{enchereId}")
    @Transactional
    public ResponseEntity<Part_En> getTopPrixProposerParten(@PathVariable Long enchereId) {
        Part_En topParten = partEnService.getTopPrixProposerParten(enchereId);
        return ResponseEntity.ok(topParten);
    }
    @GetMapping("/{enchereId}/participation/{userId}")
    public ResponseEntity<Map<String, Boolean>> checkParticipation(@PathVariable Long enchereId, @PathVariable Long userId) {
        boolean participated = partEnService.checkUserParticipation(userId, enchereId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("participated", participated);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Part_En>> getAllPartWithEnchersAndUsers() {
        List<Part_En> partEns = partEnService.getAllPartWithEnchersAndUsers();
        for (Part_En partEn : partEns) {
            Hibernate.initialize(partEn.getEnchere());

        }
        return new ResponseEntity<>(partEns, HttpStatus.OK);
    }
    @GetMapping("/userDetails/{userId}")
    public ResponseEntity<User> getUserDetailsById(@PathVariable Long userId) {
        User user = partEnService.getUserDetailsById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/topProposedPrice/{enchereId}/userDetails")
    public ResponseEntity<Long> getUserIdForTopProposedPrice(@PathVariable Long enchereId) {
        Long userId = partEnService.getUserIdForTopProposedPrice(enchereId);
        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getPartenIdByEnchere/{enchereId}")
    public ResponseEntity<Long> getPartenIdByEnchere(@PathVariable Long enchereId) {
        try {
            Long partenId =partEnService.getPartenIdByEnchere(enchereId);
            return ResponseEntity.ok(partenId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{userId}/{enchereId}")
    @Transactional
    public ResponseEntity<Long> getPartenIdByUserId(@PathVariable Long userId, @PathVariable Long enchereId) {
        Long partenId = partEnService.getPartenIdByUserId(userId, enchereId);
        if (partenId != null) {
            return ResponseEntity.ok(partenId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/parten-id")
    public ResponseEntity<Long> getPartenIdByUserId(@PathVariable Long userId) {
        Long partenId = userService.getPartenIdByUserId(userId);
        if (partenId != null) {
            return ResponseEntity.ok(partenId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getPartEnWithAssociations/{id}")
    public ResponseEntity<Part_En> getPartEnWithAssociations(@PathVariable Long id) {
        Part_En partEn = partEnService.getPart_EnWithAssociations(id);
        if (partEn != null) {
            return ResponseEntity.ok(partEn);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Part_En> addPart_En(@RequestBody Part_En partEn) {
        Part_En newPartEn = partEnService.savePartEn(partEn);
        return new ResponseEntity<>(newPartEn, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<Part_En> updatePart_En(@RequestBody Part_En partEn) {
        Part_En updatedPartEn = partEnService.updatePart_En(partEn);
        return new ResponseEntity<>(updatedPartEn, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePart_En(@PathVariable("id") Long id) {
        partEnService.deletePartEn(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

   @GetMapping("/getUserIdByIdParten/{partenId}")
   public ResponseEntity<User> getUserIdByIdParten(@PathVariable Long partenId) {
       try {
           User userId = partEnService.getUserIdByPartenId(partenId);
           return ResponseEntity.ok(userId);
       } catch (EntityNotFoundException e) {
           return ResponseEntity.notFound().build();
       }
   }
    @GetMapping("/getTopUserIdByEnchereId/{enchereId}")
    @Transactional()
    public ResponseEntity<User> getTopUserIdByEnchereId(@PathVariable Long enchereId) {
        User topUser = partEnService.getTopUserIdByEnchereId(enchereId);
        if (topUser != null) {
            return ResponseEntity.ok(topUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}