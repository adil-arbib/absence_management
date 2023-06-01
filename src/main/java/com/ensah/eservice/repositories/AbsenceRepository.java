package com.ensah.eservice.repositories;

import com.ensah.eservice.models.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    /** private Long id;
     private Date absenceStart;
     private Date absenceEnd;
     private AbsenceEtat etat;
     private TypeSeance typeSeance;
     private Enseignant enseignant;
     private Etudiant etudiant;**/




}
