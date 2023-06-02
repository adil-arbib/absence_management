package com.ensah.eservice.repositories;

import com.ensah.eservice.models.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
}
