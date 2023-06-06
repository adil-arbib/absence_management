package com.ensah.eservice.services.structure_pedagogique;


import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.elements.ElementMapper;
import com.ensah.eservice.dto.modules.ModuleDTO;
import com.ensah.eservice.dto.modules.ModuleMapper;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Element;
import com.ensah.eservice.models.Module;
import com.ensah.eservice.repositories.ElementRepository;
import com.ensah.eservice.repositories.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;

    private final ElementRepository elementRepository;

    private final ModuleMapper moduleMapper;

    private final ElementMapper elementMapper;




    public ModuleDTO create(ModuleDTO moduleDTO, List<Long> elementsIds) throws AlreadyExistsException, NotFoundException {
        if(moduleRepository.existsByNomOrCode(moduleDTO.getNom(), moduleDTO.getCode()))
            throw  new AlreadyExistsException();

        Module module = moduleMapper.toCreateModule(moduleDTO);
        if(elementsIds != null && !elementsIds.isEmpty()){
            List<Element> elements = new ArrayList<>();
            for(Long id : elementsIds) {
                Element element = elementRepository.findById(id).orElseThrow(NotFoundException::new);
                elements.add(element);
            }
            module.setElements(elements);
        }
        return moduleMapper.moduleToModuleDTO(moduleRepository.save(module));
    }

    public void update(ModuleDTO moduleDTO) throws NotFoundException {
        Module module = moduleRepository.findById(moduleDTO.getId()).orElseThrow(NotFoundException::new);
        moduleMapper.updateModuleFromDTO(moduleDTO, module);
        moduleRepository.save(module);
    }

    public List<ModuleDTO> getAll(){
        return moduleMapper.toModuleDTOList(moduleRepository.findAll());
    }

    public ModuleDTO getModuleById(Long id) throws NotFoundException {
        return moduleMapper.moduleToModuleDTO(moduleRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public Page<ModuleDTO> findByNomContains(int page, int size, String keyword) {
        Page<Module> modulePage = moduleRepository.findByNomContains(keyword, PageRequest.of(page, size));
        return modulePage.map(moduleMapper::moduleToModuleDTO);
    }

    public Page<ModuleDTO> getAll(int page, int size){
        return moduleRepository.findAll(PageRequest.of(page, size))
                .map(moduleMapper::moduleToModuleDTO);
    }

    public Page<ModuleDTO> getModulesPage(int page, int size, String keyword){
        return keyword.isEmpty()
                ? getAll(page, size)
                : findByNomContains(page, size, keyword);
    }

    public List<ElementDTO> getRestElements(Long moduleId) throws NotFoundException {
        Module module = moduleRepository.findById(moduleId).orElseThrow(NotFoundException::new);
        List<Element> elements = elementRepository.findByIdNotIn(module.getElements().stream().map(Element::getId).collect(Collectors.toList()));
        return elements.isEmpty()
                ? elementRepository.findAll().stream().map(elementMapper::toElementDTO).collect(Collectors.toList())
                : elements.stream().map(elementMapper::toElementDTO).collect(Collectors.toList());
    }

    public void addElementToModule(Long moduleId, List<Long> elementIds) throws NotFoundException {
        Module module = moduleRepository.findById(moduleId).orElseThrow(NotFoundException::new);
        module.getElements().addAll(elementRepository.findByIdIn(elementIds));
        moduleRepository.save(module);
    }

    public void removeElementFromModule(Long moduleId, Long elementId) throws NotFoundException {
        Module module = moduleRepository.findById(moduleId).orElseThrow(NotFoundException::new);
        Element element = elementRepository.findById(elementId).orElseThrow(NotFoundException::new);
        module.getElements().remove(element);
        moduleRepository.save(module);
    }

    public void deleteModule(Long id) throws NotFoundException {
        Module module = moduleRepository.findById(id).orElseThrow(NotFoundException::new);
        moduleRepository.delete(module);
    }

}
