package com.ensah.eservice.dto.users.comptes;

import com.ensah.eservice.models.Compte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompteMapper {

   @Mapping(target = "enabled", defaultValue = "true")
   @Mapping(target = "accountNotExpired", defaultValue = "true")
   @Mapping(target = "accountNotLocked", defaultValue = "true")
   @Mapping(target = "disconnectAccount", defaultValue = "false")
   @Mapping(target = "utilisateur", ignore = true)
   @Mapping(target = "password", ignore = true)
   Compte toCompte(CompteDTO compteDTO);

}
