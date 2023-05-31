package com.ensah.eservice.repositories;

import com.ensah.eservice.models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

    Optional<Compte> findByUsername(String username);

    boolean existsByUtilisateurId(Long id);

    boolean existsByUsername(String username);

}
