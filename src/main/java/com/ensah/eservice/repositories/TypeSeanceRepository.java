package com.ensah.eservice.repositories;


import com.ensah.eservice.models.TypeSeance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeSeanceRepository extends JpaRepository<TypeSeance, Long> {
}
