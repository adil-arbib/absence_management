package com.ensah.eservice.repositories;


import com.ensah.eservice.models.Accreditation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccreditationRepository extends JpaRepository<Accreditation, Long> {

   List<Accreditation> findByFinAccreditationBefore(Date date);


   List<Accreditation> findByFinAccreditationAfterAndFinAccreditationNull(Date date);

}
