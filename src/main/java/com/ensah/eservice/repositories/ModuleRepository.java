package com.ensah.eservice.repositories;


import com.ensah.eservice.models.Element;
import com.ensah.eservice.models.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {


    boolean existsByNomOrCode(String nom, String code);

    Page<Module> findByNomContains(String nom, Pageable pageable);



}
