package com.example.BidBackend.service;

import com.example.BidBackend.model.Part_En;

import java.util.List;

public interface Part_EnService {
    Long getPartenIdByEnchere(Long enchereId);
    Part_En savePartEn(Part_En partEn);
    List<Part_En> getAllPartEns();
    Part_En getPartEnById(long id);
    void deletePartEn(long id);
    Part_En updatePart_En(Part_En partEn);
    List<Part_En> getAllPartWithEnchersAndUsers() ;
    Part_En findById(Long id);
    Part_En getPart_EnWithAssociations(Long id);
}
