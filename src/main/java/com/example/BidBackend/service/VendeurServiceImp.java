package com.example.BidBackend.service;
import java.util.List;
import java.util.Optional;

import com.example.BidBackend.model.Enchere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BidBackend.model.Vendeur;
import com.example.BidBackend.repository.VendeurRepository;
@Service
public class VendeurServiceImp implements VendeurService {
	 @Autowired
	    private VendeurRepository vr;
	@Override
	public Vendeur save(Vendeur v) {
		return vr.save(v);
	}

	 @Override
	    public String deleteVendeur(long id) {
	        vr.deleteById(id);
	        return "Vendeur deleted successfully!";
	    }

	@Override
	public List<Vendeur> getAllVendeurs() {
		return vr.findAll();
	}

	 @Override
	    public Vendeur updateVendeur(Long id, Vendeur v) {
	        Optional<Vendeur> existingVendeurOptional = vr.findById(id);
	        if (existingVendeurOptional.isPresent()) {
	            Vendeur existingVendeur = existingVendeurOptional.get();
	            existingVendeur.setArticles(v.getArticles());
	            return vr.save(existingVendeur);
	        } else {
	            return null;
	        }
	    }

	public Vendeur findById(long id) {

		Optional<Vendeur> optionalVendeur = vr.findById(id);
		return optionalVendeur.orElse(null);
	}
}
