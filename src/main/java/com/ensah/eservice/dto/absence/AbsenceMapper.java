package com.ensah.eservice.dto.absence;

import com.ensah.eservice.dto.TypeSeance.TypeSeanceMapper;
import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.inscription.InscriptionMapper;
import com.ensah.eservice.dto.users.enseignant.EnseignantMapper;
import com.ensah.eservice.dto.users.etudiant.EtudiantMapper;
import com.ensah.eservice.models.Absence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
                  EtudiantMapper.class,
                  EnseignantMapper.class,
                  TypeSeanceMapper.class,
                  ElementDTO.class,
                InscriptionMapper.class
})
public interface AbsenceMapper {
   Absence toAbsence(AbsenceDTO absenceDTO);

   AbsenceDTO toAbsenceDTO(Absence absence);

   List<AbsenceDTO> toAbsenceDTOList(Collection<Absence> absences);


   @Mapping(target = "element", source = "absenceDTO.element")
   @Mapping(target = "enseignant", source = "absenceDTO.enseignant")
   @Mapping(target = "inscription", source = "absenceDTO.inscription")
   @Mapping(target = "typeSeance", source = "absenceDTO.typeSeance")
   void updateAbsenceFromDTO(AbsenceDTO absenceDTO, @MappingTarget Absence absence);

}
