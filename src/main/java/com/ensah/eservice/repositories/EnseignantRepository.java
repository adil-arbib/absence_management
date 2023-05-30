package com.ensah.eservice.repositories;


import com.ensah.eservice.models.Enseignant;
import com.ensah.eservice.models.Niveau;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant , Long> {

    Optional<Enseignant> findByCin(String cin);

    boolean existsByCin(String cni);

    List<Enseignant> findByIdNotIn(List<Long> ids);
    List<Enseignant> findByIdIn(List<Long> ids);
    Page<Enseignant> findByNomContainsOrPrenomContainsOrCinContains(String nom, String prenom, String cin, Pageable pageable);

}
