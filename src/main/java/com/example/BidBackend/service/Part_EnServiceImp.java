package com.example.BidBackend.service;

import com.example.BidBackend.model.Enchere;
import com.example.BidBackend.model.Part_En;
import com.example.BidBackend.repository.Part_EnRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class Part_EnServiceImp implements Part_EnService {
    @Autowired
    private Part_EnRepository partEnRepository;

    @Override
    public Part_En savePartEn(Part_En partEn) {
        return partEnRepository.save(partEn);
    }
    @Override
    @Transactional
    public List<Part_En> getAllPartWithEnchersAndUsers() {
        List<Part_En> parts = partEnRepository.findAll();
        parts.forEach(part -> {
            Hibernate.initialize(part.getEncheres());
            Hibernate.initialize(part.getUsers());
        });
        return parts;
    }
 /*   @Override
    public Part_En getPart_EnWithAssociations(Long id) {
        return partEnRepository.findPart_EnWithAssociations(id);
    }*/
    @Override
    @Transactional
    public Part_En getPart_EnWithAssociations(Long id) {
        Part_En partEn = partEnRepository.findById(id).orElse(null);
        if (partEn != null) {
            // Initialiser la collection encheres
            Hibernate.initialize(partEn.getEncheres());
            Hibernate.initialize(partEn.getUsers());
        }

        return partEn;
    }
    @Override
    public Part_En findById(Long id) {
        Optional<Part_En> partEnOptional = partEnRepository.findById(id);
        if (partEnOptional.isPresent()) {
            Part_En partEn = partEnOptional.get();
            // Charger les listes depuis la base de données
            partEn.getEncheres().size(); // Charger la liste d'enchères
            partEn.getUsers().size(); // Charger la liste d'utilisateurs
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
    public Part_En getPartEnById(long id) {
        return partEnRepository.findById(id).orElse(null);
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
            existingPartEn.setEncheres(partEn.getEncheres());
            existingPartEn.setUsers(partEn.getUsers());
            return partEnRepository.save(existingPartEn);
        } else {
            return null;
        }
    }
}
