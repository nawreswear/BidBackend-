package com.example.BidBackend.service;

import com.example.BidBackend.model.User;
import com.example.BidBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    public User save(User user) {
        return userRepository.save(user);
    }
    public Long findUserIdByNom(String nom) {
        User user = userRepository.findByNom(nom);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le nom : " + nom);
        }
        return user.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByNom(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'adresse email : " + username);
        }
        return UserDetailsImpl.build(user.getType(), user);
    }
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getNom(),
                    user.getPassword(),
                    // Ajoutez les rôles et les autorisations de l'utilisateur ici
                    // Pour l'exemple, je vais utiliser une liste vide
                    Collections.emptyList()
            );
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public void update(User updatedUser) {
        userRepository.findById(updatedUser.getId()).ifPresent(existingUser -> {
            // Update other fields
           /* existingUser.setType(updatedUser.getType());
            existingUser.setNom(updatedUser.getNom());
            existingUser.setPrenom(updatedUser.getPrenom());
            existingUser.setTel(updatedUser.getTel());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setCodePostal(updatedUser.getCodePostal());
            existingUser.setPays(updatedUser.getPays());
            existingUser.setVille(updatedUser.getVille());
            existingUser.setCin(updatedUser.getCin());
            existingUser.setLongitude(updatedUser.getLongitude());
            existingUser.setLatitude(updatedUser.getLatitude());*/
            existingUser.setParten(updatedUser.getParten());
            // Update the photo only if a new photo is provided
           /* if (updatedUser.getPhoto() != null && updatedUser.getPhoto().length() > 0) {
                existingUser.setPhoto(updatedUser.getPhoto());
            }*/

            // Save the updated user
            userRepository.save(existingUser);
        });
    }
}
