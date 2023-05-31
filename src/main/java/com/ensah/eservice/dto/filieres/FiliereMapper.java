package com.ensah.eservice.dto.filieres;


import com.ensah.eservice.models.Filiere;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FiliereMapper {

    @Mapping(target = "niveaux",ignore = true)
    @Mapping(target = "accreditations",ignore = true)
    Filiere createFilier(FiliereDTO filiereDTO);

    @Mapping(target = "niveaux", source = "niveaux")
    @Mapping(target = "accreditations", source = "accreditations")
    FiliereDTO toFiliereDTO(Filiere filiere);

    List<FiliereDTO> toFilierDTOList(List<Filiere> filiereList);

    @Mapping(target = "niveaux",ignore = true)
    @Mapping(target = "accreditations",ignore = true)
    void updateFiliereFromDTO(FiliereDTO filiereDTO, @MappingTarget Filiere filiere);



}
