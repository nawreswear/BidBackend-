package com.example.BidBackend.controller;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.BidBackend.model.Part_En;
import com.example.BidBackend.service.Part_EnService;

import java.util.List;

@Controller
@RequestMapping("/parten")
@CrossOrigin(origins = "http://localhost:4200")
public class Part_EnController {

    @Autowired
    private Part_EnService partEnService;

   /* @GetMapping("/all")
    public ResponseEntity<List<Part_En>> getAllPart_En() {
        List<Part_En> partEns = partEnService.getAllPartEns();
        return new ResponseEntity<>(partEns, HttpStatus.OK);
    }*/
   @GetMapping("/all")
   public ResponseEntity<List<Part_En>> getAllPartWithEnchersAndUsers() {
       List<Part_En> partEns = partEnService.getAllPartWithEnchersAndUsers();

       // Initialize lazy-loaded collections before returning
       for (Part_En partEn : partEns) {
           Hibernate.initialize(partEn.getEncheres());
           // Ajoutez d'autres initialisations si nécessaire pour d'autres collections
       }

       return new ResponseEntity<>(partEns, HttpStatus.OK);
   }
    @GetMapping("/{id}")
    public ResponseEntity<Part_En> getPart_EnById(@PathVariable("id") Long id) {
        Part_En partEn = partEnService.getPartEnById(id);
        return new ResponseEntity<>(partEn, HttpStatus.OK);
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
}
