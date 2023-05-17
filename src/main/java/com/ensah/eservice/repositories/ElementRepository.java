package com.ensah.eservice.repositories;


import com.ensah.eservice.models.Element;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {

    boolean existsByNomOrCode(String nom, String code);

    Optional<Element> findByNom(String nom);

    Page<Element> findByNomContains(String nom, Pageable pageable);


    List<Element> findByIdNotIn(List<Long> ids);

    List<Element> findByIdIn(List<Long> ids);

}
