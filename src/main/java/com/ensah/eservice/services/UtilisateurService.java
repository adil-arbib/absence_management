package com.ensah.eservice.services;

import com.ensah.eservice.models.Compte;
import com.ensah.eservice.models.Utilisateur;
import com.ensah.eservice.repositories.CompteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilisateurService implements UserDetailsService {

    private final CompteRepository compteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Compte compte = compteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Utilisateur avec nom d'utilisateur %s non trouvé", username)
                ));
        if(!compte.isEnabled()) throw new DisabledException("Votre compte est désactivé. Veuillez contacter l'administrateur.");

        return compte;
    }
}
