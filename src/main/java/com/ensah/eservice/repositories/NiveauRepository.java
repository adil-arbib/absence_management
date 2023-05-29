package com.ensah.eservice.repositories;


import com.ensah.eservice.models.Niveau;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long> {

    boolean existsByAliasOrTitre(String alias, String Titre);
    Page<Niveau> findByAliasContains(String alias, Pageable pageable);
}
