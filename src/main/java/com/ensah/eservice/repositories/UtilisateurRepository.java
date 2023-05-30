package com.ensah.eservice.repositories;

import com.ensah.eservice.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

   boolean existsByEmail(String email);

}
