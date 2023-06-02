package com.ensah.eservice.dto.absence;

import com.ensah.eservice.dto.TypeSeance.TypeSeanceMapper;
import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.users.enseignant.EnseignantMapper;
import com.ensah.eservice.dto.users.etudiant.EtudiantMapper;
import com.ensah.eservice.models.Absence;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
                  EtudiantMapper.class,
                  EnseignantMapper.class,
                  TypeSeanceMapper.class,
                  ElementDTO.class
})
public interface AbsenceMapper {
   Absence toAbsence(AbsenceDTO absenceDTO);

   AbsenceDTO toAbsenceDTO(Absence absence);

   List<AbsenceDTO> toAbsenceDTOList(Collection<Absence> absences);


   void updateAbsenceFromDTO(AbsenceDTO absenceDTO, @MappingTarget Absence absence);

}
