package com.example.BidBackend.service;

import java.util.List;

import com.example.BidBackend.model.Vendeur;

public interface VendeurService {
	Vendeur save(Vendeur v);
    String deleteVendeur(long id) ;
    List<Vendeur> getAllVendeurs();
    Vendeur updateVendeur(Long id,Vendeur v);
    List<Vendeur> getAllVendeursForEnchere(Long enchereId);
}
