package com.ensah.eservice.repositories;


import com.ensah.eservice.models.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {

    boolean existsByNomOrCode(String nom, String code);

}
