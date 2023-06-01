package com.ensah.eservice.dto.users.enseignant;

import com.ensah.eservice.dto.files.FileMapper;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.models.Enseignant;
import com.ensah.eservice.models.Etudiant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FileMapper.class})
public interface EnseignantMapper {

   EnseignantDTO toEnseignantDTO(Enseignant enseignant);


   @Mapping(target = "image", ignore = true)
   @Mapping(target = "email", source = "email")
   Enseignant toEnseignant(EnseignantDTO enseignantDTO);

   @Mapping(target = "image", ignore = true)
   List<EnseignantDTO> toEnseignantDTOList(List<Enseignant> etudiants);


   @Mapping(target = "image", ignore = true)
   void update(EnseignantDTO enseignantDTO, @MappingTarget Enseignant enseignant);
}
