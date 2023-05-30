package com.ensah.eservice.repositories;

import com.ensah.eservice.models.CadreAdministrateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadreAdministrateurRepository extends JpaRepository<CadreAdministrateur, Long> {

   boolean existsByCin(String cin);
   Page<CadreAdministrateur> findByNomContainsOrPrenomContainsOrEmailContains(String nom, String prenom, String email, Pageable pageable);

}
