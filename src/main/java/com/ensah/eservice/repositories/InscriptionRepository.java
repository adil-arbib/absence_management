package com.ensah.eservice.repositories;


import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.models.Etudiant;
import com.ensah.eservice.models.Inscription;
import com.ensah.eservice.models.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    List<Inscription> findAllByAnneeAndNiveau(Integer annee, Niveau niveau);

    Optional<Inscription> findByEtudiantAndAnnee(Etudiant etudiant, Integer annee);
}
