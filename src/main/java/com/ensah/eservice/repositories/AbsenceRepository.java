package com.ensah.eservice.repositories;

import com.ensah.eservice.models.Absence;
import com.ensah.eservice.models.Inscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    /** private Long id;
     private Date absenceStart;
     private Date absenceEnd;
     private AbsenceEtat etat;
     private TypeSeance typeSeance;
     private Enseignant enseignant;
     private Etudiant etudiant;**/

    Page<Absence> findByInscriptionAndElementCodeContains(Inscription inscription, String keyword, Pageable pageable);

    List<Absence> findAllByEnseignant_Id(Long Id);

    Page<Absence> findByInscriptionAndElementNomContains(Inscription inscription, String Nom, Pageable pageable);

}
