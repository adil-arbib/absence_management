package com.ensah.eservice.dto.users.etudiant;

import com.ensah.eservice.dto.files.FileMapper;
import com.ensah.eservice.models.Etudiant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {FileMapper.class})
public interface EtudiantMapper {


   EtudiantDTO toEtudiantDTO(Etudiant etudiant);


   @Mapping(target = "image", ignore = true)
   @Mapping(target = "email", source = "email")
   Etudiant toEtudiant(EtudiantDTO etudiantDTO);


   @Mapping(target = "image", ignore = true)
   void update(EtudiantDTO etudiantDTO, @MappingTarget Etudiant etudiant);


}
