package com.ensah.eservice.repositories;

import com.ensah.eservice.models.Etudiant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {


   boolean existsByCne(String cne);


   Page<Etudiant> findByNomContainsAndDeletedFalseOrPrenomContainsAndDeletedFalseOrNomArabeContainsAndDeletedFalseOrPrenomArabContainsAndDeletedFalse(String nom, String prenom, String nomArab, String prenomArab, Pageable pageable);


   Page<Etudiant> findByNomContainsAndDeletedTrueOrPrenomContainsAndDeletedTrueOrNomArabeContainsAndDeletedTrueOrPrenomArabContainsAndDeletedTrue(String nom, String prenom, String nomArab, String prenomArab, Pageable pageable);


}
