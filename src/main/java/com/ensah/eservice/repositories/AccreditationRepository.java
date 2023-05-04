package com.ensah.eservice.repositories;


import com.ensah.eservice.models.Accreditation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccreditationRepository extends JpaRepository<Accreditation, Long> {
}
