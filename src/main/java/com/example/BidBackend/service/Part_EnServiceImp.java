package com.example.BidBackend.service;

import com.example.BidBackend.exception.ResourceNotFoundException;
import com.example.BidBackend.model.Article;
import com.example.BidBackend.model.Enchere;
import com.example.BidBackend.model.Part_En;
import com.example.BidBackend.model.User;
import com.example.BidBackend.repository.ArticleRepository;
import com.example.BidBackend.repository.EnchereRepository;
import com.example.BidBackend.repository.Part_EnRepository;
import com.example.BidBackend.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class Part_EnServiceImp implements Part_EnService {
    @Autowired
    private Part_EnRepository partEnRepository;
    @Autowired
    private EnchereRepository enchereRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public boolean checkUserParticipation(Long userId, Long enchereId) {
        Enchere enchere = enchereRepository.findById(enchereId)
                .orElseThrow(() -> new EntityNotFoundException("Enchère non trouvée"));

        return partEnRepository.existsByUser_IdAndEnchere_Id(userId, enchereId);
    }

    @Override
    @Transactional
    public void addPrixVenteForParten(Long partenId, Long enchereId, double prixProposer) {
        // Find Part_En by ID
        Part_En partEn = partEnRepository.findById(partenId)
                .orElseThrow(() -> new ResourceNotFoundException("Part_En not found with id " + partenId));

        // Find Enchere by ID
        Enchere enchere = enchereRepository.findById(enchereId)
                .orElseThrow(() -> new EntityNotFoundException("Enchère non trouvée"));

        // Check if the auction is still ongoing
        Date now = new Date();
        if (now.after(enchere.getDateFin())) {
            updateEnchersTerminer(enchere);
            throw new IllegalStateException("L'enchère est terminée, vous ne pouvez pas mettre à jour le prix de l'article.");
        }

        // Update the proposed price if the new price is higher
        if (prixProposer > partEn.getPrixproposer()) {
            partEn.setPrixproposer(prixProposer);
            partEnRepository.save(partEn);
        }
    }

    @Override
    public User getUserDetailsById(Long userId) {
        // Récupérer l'utilisateur par son ID
        return userRepository.findById(userId).orElse(null);
    }
    @Override
    public Long getUserIdForTopProposedPrice(Long enchereId) {
        // Récupérer les données de Part_En
        Part_En partEn = partEnRepository.findTopPrixProposerPartenByEnchereId(enchereId);

        // Vérifier si Part_En est null
        if (partEn == null || partEn.getUser() == null) {
            return null;
        }

        // Retourner l'ID de l'utilisateur
        return partEn.getUser().getId();
    }
    @Override
    @Transactional
    public Long getPartenIdByUserId(Long userId, Long enchereId) {
        // Récupérer l'enchère
        Enchere enchere = enchereRepository.findById(enchereId).orElse(null);
        if (enchere != null) {
            // Vérifier si l'enchère a des participations
            List<Part_En> participations = enchere.getParten();
            if (participations != null && !participations.isEmpty()) {
                // Parcourir les participations pour trouver celle associée à l'utilisateur
                for (Part_En participation : participations) {
                    if (participation.getUser().getId().equals(userId)) {
                        return participation.getId();
                    }
                }
            }
        }
        return -1L;
    }
    @Override
    @Transactional
    public Part_En getTopPrixProposerParten(Long enchereId) {
        // Vérifiez que l'enchèreId est bien de type Long
        if (enchereId == null || enchereId <= 0) {
            throw new IllegalArgumentException("L'ID de l'enchère doit être un entier positif non nul");
        }

        // Recherchez l'enchère par ID
        Enchere enchere = enchereRepository.findById(enchereId)
                .orElseThrow(() -> new EntityNotFoundException("Enchère non trouvée"));

        Date now = new Date();
        if (now.after(enchere.getDateFin()) && !"termine".equals(enchere.getEtat())) {
            updateEnchersTerminer(enchere);
            Part_En partEn = partEnRepository.findTopPrixProposerPartenByEnchereId(enchereId);
            updatePart_EnSiEnchersTerminer(partEn);
            return partEn;
        }
        Part_En partEn = partEnRepository.findTopPrixProposerPartenByEnchereId(enchereId);

        // Récupérer l'utilisateur pour l'enchère
        User user = partEn.getUser();
        if (user != null) {
            // Récupérer l'ID de l'utilisateur et le définir dans Part_En
            Long userId = user.getId();
            partEn.setUserId(userId);
        }

        return partEn;
    }

    public Article updateArticledansEnchers(Article article) {
        Optional<Article> optionalArticle = articleRepository.findById(article.getId());
        if (optionalArticle.isPresent()) {
            Article existingart = optionalArticle.get();
            existingart.setStatut("Payer");
            return articleRepository.save(existingart);
        } else {
            return null;
        }







    }
    public Enchere updateEnchersTerminer(Enchere enchere) {
        Optional<Enchere> optionalEnchere = enchereRepository.findById(enchere.getId());
        if (optionalEnchere.isPresent()) {
            Enchere existingEnchere = optionalEnchere.get();
            existingEnchere.setEtat("termine");
            return enchereRepository.save(existingEnchere);
        } else {
            return null;
        }
    }
    @Override
    public Part_En updatePart_EnSiEnchersTerminer(Part_En partEn) {
        Optional<Part_En> optionalPartEn = partEnRepository.findById(partEn.getId());
        if (optionalPartEn.isPresent()) {
            Part_En existingPartEn = optionalPartEn.get();
            existingPartEn.setEtat("gangnat");
            return partEnRepository.save(existingPartEn);
        } else {
            return null;
        }
    }
    @Override
    public Part_En savePartEn(Part_En partEn) {
        return partEnRepository.save(partEn);
    }
    @Override
    @Transactional
    public List<Part_En> getAllPartWithEnchersAndUsers() {
        List<Part_En> parts = partEnRepository.findAll();
        parts.forEach(part -> {
            Hibernate.initialize(part.getEnchere());
            Hibernate.initialize(part.getUser());
        });
        return parts;
    }

 @Override
 public Long getPartenIdByEnchere(Long enchereId) {
     Enchere enchere = enchereRepository.findById(enchereId)
             .orElseThrow(() -> new EntityNotFoundException("Enchère non trouvée"));

     // Vérifiez si l'enchère a des participations
     if (!enchere.getParten().isEmpty()) {
         // Récupérez le premier Part_En associé à l'enchère
         Part_En partEn = enchere.getParten().get(0);
         return partEn.getId();
     } else {
         return null; // Ou renvoyer une valeur par défaut selon votre cas
     }
 }
    @Override
    @Transactional
    public Part_En getPart_EnWithAssociations(Long id) {
        Part_En partEn = partEnRepository.findById(id).orElse(null);
        if (partEn != null) {
            // Initialiser la collection encheres
            Hibernate.initialize(partEn.getEnchere());
            Hibernate.initialize(partEn.getUser());
        }

        return partEn;
    }
    @Override
    public Part_En findById(Long id) {
        Optional<Part_En> partEnOptional = partEnRepository.findById(id);
        if (partEnOptional.isPresent()) {
            Part_En partEn = partEnOptional.get();
            // Charger les listes depuis la base de données
            partEn.getEnchere();
            partEn.getUser();
            return partEn;
        } else {
            throw new RuntimeException("Part_En not found with id: " + id);
        }
    }
    @Override
    public List<Part_En> getAllPartEns() {
        return partEnRepository.findAll();
    }



    @Override
    public void deletePartEn(long id) {
        partEnRepository.deleteById(id);
    }

    @Override
    public Part_En updatePart_En(Part_En partEn) {
        Optional<Part_En> optionalPartEn = partEnRepository.findById(partEn.getId());
        if (optionalPartEn.isPresent()) {
            Part_En existingPartEn = optionalPartEn.get();
            existingPartEn.setEnchere(partEn.getEnchere());
            existingPartEn.setUser(partEn.getUser());
            return partEnRepository.save(existingPartEn);
        } else {
            return null;
        }
    }
    @Override
    public User getUserIdByPartenId(Long partenId) {
        Part_En partEn = partEnRepository.findById(partenId)
                .orElseThrow(() -> new EntityNotFoundException("Partenaire non trouvé avec l'id " + partenId));

        return partEn.getUser();
    }
    @Transactional()
    public User getTopUserIdByEnchereId(Long enchereId) {
        Part_En topParten = partEnRepository.findTopByEnchereIdOrderByPrixproposerDesc(enchereId);
        if (topParten != null) {
            return topParten.getUser();
        }
        return null;
    }
}
