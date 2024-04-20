package com.example.BidBackend.service;

import com.example.BidBackend.model.Admin;
import com.example.BidBackend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AdminServiceImp implements AdminService{
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin save(Admin a) {
        return adminRepository.save(a);
    }

    @Override
    public Admin findById(long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        return adminOptional.orElse(null);
    }
}
