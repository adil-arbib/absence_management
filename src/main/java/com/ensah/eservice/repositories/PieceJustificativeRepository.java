package com.ensah.eservice.repositories;


import com.ensah.eservice.models.PieceJustificative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PieceJustificativeRepository extends JpaRepository<PieceJustificative, Long> {
}
