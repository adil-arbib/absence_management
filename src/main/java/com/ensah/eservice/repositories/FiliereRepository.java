package com.ensah.eservice.repositories;

import com.ensah.eservice.models.Filiere;
import com.ensah.eservice.models.Niveau;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {


    Optional<Filiere> findByNom(String nom);

    Optional<Filiere> findByNomOrAlias(String nom, String alias);
    boolean existsByNomOrAlias(String nom, String alias);

    Page<Filiere> findByNomContains(String Nom, Pageable pageable);




}
