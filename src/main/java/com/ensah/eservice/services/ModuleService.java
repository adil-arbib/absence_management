package com.ensah.eservice.services;


import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.elements.ElementMapper;
import com.ensah.eservice.dto.modules.ModuleDTO;
import com.ensah.eservice.dto.modules.ModuleMapper;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.models.Element;
import com.ensah.eservice.models.Module;
import com.ensah.eservice.repositories.ElementRepository;
import com.ensah.eservice.repositories.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;

    private final ElementRepository elementRepository;

    private final ModuleMapper moduleMapper;

    public Module create(ModuleDTO moduleDTO) throws AlreadyExistsException {
        if(moduleRepository.existsByNomOrCode(moduleDTO.getNom(), moduleDTO.getCode()))
            throw  new AlreadyExistsException();

        Module module = moduleMapper.toCreateModule(moduleDTO);
        if(moduleDTO.getElements() != null){
            for( ElementDTO elementDTO : moduleDTO.getElements()){

                Element element = elementRepository.findByNom(elementDTO.getNom()).
                        orElseThrow();
                module.getElements().add(element);
            }
        }

        return moduleRepository.save(module);
    }

    public List<ModuleDTO> getALl(){
        return moduleMapper.toModuleDTOList(moduleRepository.findAll());
    }

}
