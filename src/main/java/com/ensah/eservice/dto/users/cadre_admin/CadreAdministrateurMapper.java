package com.ensah.eservice.dto.users.cadre_admin;

import com.ensah.eservice.dto.files.FileMapper;
import com.ensah.eservice.dto.users.enseignant.EnseignantDTO;
import com.ensah.eservice.models.CadreAdministrateur;
import com.ensah.eservice.models.Enseignant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {FileMapper.class})
public interface CadreAdministrateurMapper {

   CadreAdministrateurDTO toCadreAdministrateurDTO(CadreAdministrateur cadreAdministrateur);


   @Mapping(target = "image", ignore = true)
   @Mapping(target = "email", source = "email")
   CadreAdministrateur toCadreAdministrateur(CadreAdministrateurDTO cadreAdministrateurDTO);


   @Mapping(target = "image", ignore = true)
   void update(CadreAdministrateurDTO cadreAdministrateurDTO, @MappingTarget CadreAdministrateur cadreAdministrateur);

}
