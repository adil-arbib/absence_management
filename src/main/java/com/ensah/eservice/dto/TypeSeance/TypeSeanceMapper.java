package com.ensah.eservice.dto.TypeSeance;

import com.ensah.eservice.models.TypeSeance;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")

public interface TypeSeanceMapper {
    TypeSeance toTypeSeance(TypeSeanceDTO typeSeanceDTO);
    TypeSeanceDTO toTypeSeanceDTO(TypeSeance typeSeance);
    List<TypeSeanceDTO> toTypeSeanceDTOList(Collection<TypeSeance> typeSeances);
    void updateTypeSeanceFromDTO(TypeSeanceDTO typeSeanceDTO, @MappingTarget TypeSeance typeSeance);

}
