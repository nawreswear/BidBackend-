package com.example.BidBackend.controller;
import com.example.BidBackend.model.Vendeur;
import com.example.BidBackend.service.VendeurServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vendeur")
//@CrossOrigin(origins = "http://localhost:3000")
public class VendeurControlleur {


    @Autowired
    VendeurServiceImp vendeurserv;
    @PostMapping(value="/save")
    public Vendeur save(@RequestBody Vendeur v) 
    { 
    	return vendeurserv.save(v); 
     }

    @PutMapping(value="/updateVendeur/{id}")
    public Vendeur updateVendeur(@PathVariable Long id, @RequestBody Vendeur v)
    {
        return vendeurserv.updateVendeur(id, v);
    }
    @DeleteMapping(value="/deleteVendeur/{id}")
    public String deleteVendeur(@PathVariable Long id)
    {
        return vendeurserv.deleteVendeur(id);
    }

    @GetMapping(value="/getAllVendeurs")
    public List<Vendeur> getAllVendeurs() 
    {
        return vendeurserv.getAllVendeurs();
    }

    @GetMapping("/enchere/{enchereId}/vendeurs")
    public List<Vendeur> getAllVendeursForEnchere(@PathVariable Long enchereId) {
        return vendeurserv.getAllVendeursForEnchere(enchereId);
    }
}