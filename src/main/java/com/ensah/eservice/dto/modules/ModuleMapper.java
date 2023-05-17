package com.ensah.eservice.dto.modules;


import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.models.Element;
import com.ensah.eservice.models.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ModuleMapper {

    Module toModule(ModuleDTO moduleDTO);

    ModuleDTO toModuleDTO(Module module);
    @Mapping(target = "elements",ignore = true)
    Module toCreateModule(ModuleDTO moduledto);



    List<ModuleDTO> toModuleDTOList(List<Module> modules);
}
