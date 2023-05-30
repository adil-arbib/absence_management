package com.ensah.eservice.dto.niveau;


import com.ensah.eservice.dto.modules.ModuleMapper;
import com.ensah.eservice.models.Niveau;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NiveauMapper {


//    ModuleMapper modulemapper = Mappers.getMapper(ModuleMapper.class);

    @Mapping(target = "modules",ignore = true)
    Niveau createNiveau(NiveauDTO niveauDTO);

    @Mapping(target = "modules", source = "modules")
    NiveauDTO toNiveauDTO(Niveau niveau);

    @Mapping(target = "modules",ignore = true)
    List<NiveauDTO> listToNiveauDTO(List<Niveau> niveauList);


    @Mapping(target = "modules",ignore = true)
    void updateNiveauFromDTO(NiveauDTO niveauDTO, @MappingTarget Niveau niveau);

}
