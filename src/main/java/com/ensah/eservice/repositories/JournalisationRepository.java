package com.ensah.eservice.repositories;


import com.ensah.eservice.models.JournalisationEvenements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalisationRepository extends JpaRepository<JournalisationEvenements, Long> {
}
