package com.ensah.eservice.repositories;


import com.ensah.eservice.models.Element;
import com.ensah.eservice.models.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {


    boolean existsByNomOrCode(String nom, String code);

    List<Module> findByIdNotIn(List<Long> ids);

    List<Module> findByIdIn(List<Long> ids);

    Page<Module> findByNomContains(String nom, Pageable pageable);




}
