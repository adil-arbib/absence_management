package com.ensah.eservice.dto.users.comptes;

import com.ensah.eservice.models.Compte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompteMapper {

   @Mapping(target = "utilisateur", ignore = true)
   @Mapping(target = "password", ignore = true)
   Compte toCompte(CompteDTO compteDTO);


   @Mapping(target = "utilisateur", ignore = true)
   @Mapping(target = "password", ignore = true)
   CompteDTO toCompteDTO(Compte compte);

   @Mapping(target = "password", ignore = true)
   @Mapping(target = "utilisateur" ,ignore = true)
   void update(CompteDTO compteDTO, @MappingTarget Compte compte);

}
