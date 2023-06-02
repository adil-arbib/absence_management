package com.ensah.eservice.repositories;


import com.ensah.eservice.models.TypeSeance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeSeanceRepository extends JpaRepository<TypeSeance, Long> {
    boolean existsByIntituleOrAlias(String intitule, String alias);

    Optional<TypeSeance> findByAlias(String alias);

}
