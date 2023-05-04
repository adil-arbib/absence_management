package com.ensah.eservice.repositories;


import com.ensah.eservice.models.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant , Long> {

    Optional<Enseignant> findByCin(String cin);

    boolean existsByCin(String cni);

}
