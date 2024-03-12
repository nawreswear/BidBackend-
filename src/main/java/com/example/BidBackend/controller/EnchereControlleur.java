package com.example.BidBackend.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BidBackend.model.Enchere;
import com.example.BidBackend.service.EnchereService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class EnchereControlleur {
	@Autowired
	EnchereService enchereService;
	
	
	@PostMapping(value="/addenchere")
	public Enchere addenchere(@RequestBody Enchere c){
		return enchereService.save(c);
	}
	@PostMapping(value="/UpdateEnchere/{iden}")
	public Enchere UpdateEnchere(@PathVariable long iden,@RequestBody Enchere c) 
	{
		return enchereService.updateEnchere(iden,c);
	}
	@DeleteMapping(value="/deleteEnchere/{iden}")
	public String deleteEnchere(@PathVariable long iden) 
	{
		return enchereService.deleteEnchere(iden);
	}
	@GetMapping(value="/getallEncheres")
	public List<Enchere> getallEncheres() {
		return enchereService.getAllEncheres();
	}
	 @GetMapping("/{id}")
	 public Enchere getEnchereById(@PathVariable Long id) {
	        return enchereService.getEnchereById(id);
	    }
	

	    @GetMapping("/article/{articleId}")
	    public List<Enchere> getEncheresByArticleId(@PathVariable Long articleId) {
	        return enchereService.getEncheresByArticleId(articleId);
	    }



	    @GetMapping("/datefin/after/{date}")
	    public List<Enchere> getEncheresByDateFinAfter(@PathVariable Date date) {
	        return enchereService.getEncheresByDateFinAfter(date);
	    }

	    @GetMapping("/datedebut/before/{date}")
	    public List<Enchere> getEncheresByDateDebutBefore(@PathVariable Date date) {
	        return enchereService.getEncheresByDateDebutBefore(date);
	    }

	    @GetMapping("/datedebut/between/{dateDebut1}/{dateDebut2}")
	    public List<Enchere> getEncheresByDateDebutBetween(@PathVariable Date dateDebut1, @PathVariable Date dateDebut2) {
	        return enchereService.getEncheresByDateDebutBetween(dateDebut1, dateDebut2);
	    }
}
