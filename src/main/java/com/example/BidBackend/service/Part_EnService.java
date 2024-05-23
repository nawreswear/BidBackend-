package com.example.BidBackend.service;

import com.example.BidBackend.model.Part_En;
import com.example.BidBackend.model.User;

import java.util.List;
import java.util.Optional;

public interface Part_EnService {
    void addPrixVenteForParten(Long partenId,Long enchereId, double Prixproposer);
    Part_En getTopPrixProposerParten(Long enchereId) ;
    Long getPartenIdByEnchere(Long enchereId);
    Part_En updatePart_EnSiEnchersTerminer(Part_En partEn);
    Part_En savePartEn(Part_En partEn);
    List<Part_En> getAllPartEns();
    //Part_En getPartEnById(long id);
    void deletePartEn(long id);
    Part_En updatePart_En(Part_En partEn);
    List<Part_En> getAllPartWithEnchersAndUsers() ;
    Part_En findById(Long id);
    boolean checkUserParticipation(Long userId, Long enchereId);
    User getUserDetailsById(Long userId);
    Long getUserIdForTopProposedPrice(Long enchereId);
    Long getPartenIdByUserId(Long userId, Long enchereId);
    Part_En getPart_EnWithAssociations(Long id);
    //Optional<Part_En> getUsersByPartenId(Long partenId);
    User getUserIdByPartenId(Long partenId);

    User getTopUserIdByEnchereId(Long enchereId);
}
