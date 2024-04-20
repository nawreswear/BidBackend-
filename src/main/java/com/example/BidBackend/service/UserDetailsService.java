package com.example.BidBackend.service;


import com.example.BidBackend.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;


public interface UserDetailsService {
    User save(User user);
    User findById (Long id);
    void update(User user);
    Long findUserIdByNom(String nom);
    UserDetails loadUserById(Long userId);
}
