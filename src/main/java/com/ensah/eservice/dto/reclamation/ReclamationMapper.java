package com.ensah.eservice.dto.reclamation;

import com.ensah.eservice.dto.absence.AbsenceMapper;
import com.ensah.eservice.dto.message.MessageMapper;
import com.ensah.eservice.models.Reclamation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AbsenceMapper.class, MessageMapper.class})
public interface ReclamationMapper {

   ReclamationDTO toReclamationDTO(Reclamation reclamation);

   @Mapping(target = "absence", ignore = true)
   @Mapping(target = "responses", ignore = true)
   Reclamation toReclamation(ReclamationDTO reclamationDTO);

}
