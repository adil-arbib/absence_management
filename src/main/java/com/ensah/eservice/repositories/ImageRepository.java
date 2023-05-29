package com.ensah.eservice.repositories;

import com.ensah.eservice.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<File, Long> {
}
