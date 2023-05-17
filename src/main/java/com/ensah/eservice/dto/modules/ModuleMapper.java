package com.ensah.eservice.dto.modules;


import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.elements.ElementMapper;
import com.ensah.eservice.models.Element;
import com.ensah.eservice.models.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ModuleMapper {

    ElementMapper elementMapper = Mappers.getMapper(ElementMapper.class);

    Module toModule(ModuleDTO moduleDTO);

    @Mapping(target = "elements",ignore = true)
    Module toCreateModule(ModuleDTO moduledto);

    @Mapping(target = "elements", source = "elements")
    ModuleDTO moduleToModuleDTO(Module module);

    List<ModuleDTO> toModuleDTOList(List<Module> modules);

    @Mapping(target = "elements", ignore = true)
    void updateModuleFromDTO(ModuleDTO moduleDTO, @MappingTarget Module module);

    default Collection<ElementDTO> mapElements(Collection<Element> elements) {
        if (elements == null) return null;
        return elements.stream()
                .map(elementMapper::toElementDTO)
                .collect(Collectors.toList());
    }

}
