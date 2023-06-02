package com.ensah.eservice.repositories;

import com.ensah.eservice.models.Absence;
import com.ensah.eservice.models.Etudiant;
import com.ensah.eservice.models.Reclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

   Page<Reclamation> findByAbsenceInscriptionEtudiant(Etudiant etudiant, Pageable pageable);


   boolean existsByAbsence(Absence absence);

}
